package com.narnia.railways.service;

import com.narnia.railways.dao.StationDAO;
import com.narnia.railways.model.*;
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

    public StationServiceImpl(StationDAO stationDAO, TrainService trainService) {
        this.stationDAO = stationDAO;
        this.trainService = trainService;
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
                .getAll()
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
         */
        long prev = 0;
        if (train.getTrainState().equals(TrainState.MOVEMENT)) {
            prev = path.getLength() - train.getMoveCounter();
            schedule.get(train.getToStation()).add(new TrainScheduleDTO(train, prev));
            prev += train.getToStation().getVal();
        } else if (train.getTrainState().equals(TrainState.STOP) || train.getTrainState().equals(TrainState.WAIT)) {
            if (train.getFromStation().getVal() - train.getMoveCounter() > 0) {
                prev = train.getFromStation().getVal() - train.getMoveCounter();
            }
            prev += path.getLength();
            schedule.get(train.getToStation()).add(new TrainScheduleDTO(train, prev));
            prev += train.getToStation().getVal();
        } else if (train.getTrainState().equals(TrainState.DEPARTURE)) {
            prev = path.getLength();
            schedule.get(train.getToStation()).add(new TrainScheduleDTO(train, prev));
            prev += train.getToStation().getVal();
        } else if (train.getTrainState().equals(TrainState.ARRIVAL)) {
            schedule.get(train.getToStation()).add(new TrainScheduleDTO(train, prev));
            prev += train.getToStation().getVal();
        }
        if (train.getDirection().equals(TrainDirect.FORWARD)) {

            for (int i = idx + 1; i < track.size(); i++) {
                Path currentPath = track.get(i);
                prev += currentPath.getLength();
                schedule.get(currentPath.getS_node()).add(
                        new TrainScheduleDTO(train, prev)
                );
                prev += currentPath.getS_node().getVal();
            }
        } else {
            for (int i = idx - 1; i >= 0; i--) {
                Path currentPath = track.get(i);
                prev += currentPath.getLength();
                schedule.get(currentPath.getF_node()).add(
                        new TrainScheduleDTO(train, prev)
                );
                prev += currentPath.getF_node().getVal();
            }
        }
    }
}
