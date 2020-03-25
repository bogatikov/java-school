package com.narnia.railways.service.impl;

import com.narnia.railways.dao.StationDAO;
import com.narnia.railways.model.*;
import com.narnia.railways.service.StationService;
import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.dto.TrainScheduleDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StationServiceImpl implements StationService {

    private final StationDAO stationDAO;

    private final TrainService trainService;

    private final TimeSimulationServiceImpl timeSimulationServiceImpl;

    public StationServiceImpl(StationDAO stationDAO, TrainService trainService, TimeSimulationServiceImpl timeSimulationServiceImpl) {
        this.stationDAO = stationDAO;
        this.trainService = trainService;
        this.timeSimulationServiceImpl = timeSimulationServiceImpl;
    }

    public List<Station> getAll() {
        return stationDAO.list();
    }

    public void save(Station station) {
        stationDAO.save(station);
    }

    public Station getById(Long id) {
        return stationDAO.getById(id);
    }

    public void delete(Station station) {
        stationDAO.delete(station);
    }

    public void update(Station station) {
        stationDAO.update(station);
    }

    public Map<Station, List<TrainScheduleDTO>> getScheduleForStations() {
        Map<Station, List<TrainScheduleDTO>> schedule = new HashMap<>();
        stationDAO.list()
                .forEach(station -> {
                    schedule.put(station, new ArrayList<>());
                });

        trainService
                .getActiveTrains()
                .forEach(train -> calculateTrainSchedule(train, schedule));

        return schedule;
    }

    public void calculateTrainSchedule(Train train, Map<Station, List<TrainScheduleDTO>> schedule) {
        // If train have no track - we do not add it to table
        Path path = train.getNextPath();
        if (train.getTrack().size() == 0)
            return;

        List<Path> track = train.getTrack();
        int idx = track.indexOf(path);

        /*
         * If a train movement forward then ticks needed to arrive at the ${train.toStation} is path.length - moveCounter
         * A train can be stopped in two case:
         * 1. The train waiting for passengers;
         *    For this case train will be on this station only for ${station.val - moveCounter} ticks
         * 2. The train has no possible to departure `cause no freeway or the ${train.toStation} capacity is zero now.
         *    For this case the train will be on the next station for ${path.length} ticks
         *
         */
        long ticksToStation = 0;
        switch (train.getTrainState()) {
            case MOVEMENT:
                ticksToStation = path.getLength() - train.getMoveCounter() + 1; // and +1 arrive tick train will be at the station through ticks
                putStationScheduleForTrain(train.getToStation(), train, ticksToStation, schedule);
                ticksToStation += train.getToStation().getVal() + 1 + 1; // train will be departed from the station through ticks
                break;
            case WAIT:
                ticksToStation = path.getLength() + 1 + 1; // train will be at the station through path length + one tick to switch to STOP state and one tick to departure
                putStationScheduleForTrain(train.getToStation(), train, ticksToStation, schedule);
                ticksToStation += train.getToStation().getVal() + 1 + 1;
                break;
            case STOP:
                if (train.getFromStation().getVal() - train.getMoveCounter() > 0) {
                    ticksToStation = train.getFromStation().getVal() - train.getMoveCounter();
                }
                ticksToStation += path.getLength() + 1 + 1 + 1;
                putStationScheduleForTrain(train.getToStation(), train, ticksToStation, schedule);
                ticksToStation += train.getToStation().getVal() + 1 + 1;
                break;
            case ARRIVAL:
                putStationScheduleForTrain(train.getToStation(), train, ticksToStation, schedule);
                ticksToStation += train.getToStation().getVal() + 1 + 1;
                break;
            case DEPARTURE:
                ticksToStation = path.getLength() + 1 + 1;
                putStationScheduleForTrain(train.getToStation(), train, ticksToStation, schedule);
                ticksToStation += train.getToStation().getVal() + 1 + 1;
                break;

        }
        switch (train.getDirection()) {
            case FORWARD:
                for (int i = idx + 1; i < track.size(); i++) {
                    Path currentPath = track.get(i);
                    ticksToStation += currentPath.getLength() + 1 + 1;
                    putStationScheduleForTrain(currentPath.getS_node(), train, ticksToStation, schedule);
                    ticksToStation += currentPath.getS_node().getVal() + 1 + 1;
                }
                break;
            case BACKWARD:
                for (int i = idx - 1; i >= 0; i--) {
                    Path currentPath = track.get(i);
                    ticksToStation += currentPath.getLength() + 1 + 1;
                    putStationScheduleForTrain(currentPath.getF_node(), train, ticksToStation, schedule);
                    ticksToStation += currentPath.getF_node().getVal() + 1 + 1;
                }
                break;
        }
    }

    private void putStationScheduleForTrain(Station station, Train train, Long ticks, Map<Station, List<TrainScheduleDTO>> schedule) {
        schedule.get(station).add(new TrainScheduleDTO(
                train,
                ticks,
                timeSimulationServiceImpl.convertTicksToTime(ticks)
        ));
    }
}
