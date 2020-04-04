package com.narnia.railways.service.impl;

import com.narnia.railways.dao.TrainDAO;
import com.narnia.railways.model.*;
import com.narnia.railways.service.PathService;
import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.Updatable;
import com.narnia.railways.service.dto.PathDTO;
import com.narnia.railways.service.dto.TrainDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.narnia.railways.model.TrainState.*;

@Service
public class TrainServiceImpl implements TrainService, Updatable {

    private final TrainDAO trainDAO;

    private final PathService pathService;

    @Autowired
    private ModelMapper modelMapper;

    public TrainServiceImpl(TrainDAO trainDAO, PathService pathService) {
        this.trainDAO = trainDAO;
        this.pathService = pathService;
    }

    @Override
    public List<Train> getAll() {
        return trainDAO.findAll();
    }

    @Override
    public List<Train> getActiveTrains() {
        return trainDAO.findAllActiveTrains();
    }


    @Override
    public void save(Train train) {
        trainDAO.save(train);
    }

    @Override
    public Train getById(Long id) {
        return trainDAO.getById(id);
    }

    @Override
    public void delete(Train train) {
        trainDAO.delete(train);
    }

    @Override
    public void update(Train train) {
        trainDAO.update(train);
    }

    @Override
    public Train updateTrainState(Train train) {
        switch (train.getTrainState()) {
            case WAIT:
                train.setTrainState(STOP);
                break;

            case STOP:
                // Use OOP when you can
                // functional approach isn't always the best in Java
                if (train.getFromStation().getVal() - train.getMoveCounter() > 0) {
                    train.move();
                } else if (train.getToStation().hasFreePlatform() &&
                        train.getNextPath().hasFreeway()
                ) {
                    // increment occupation counter
                    train.getToStation().reservePlatform();
                    pathService.reserveFreeway(train.getNextPath());
                    train.setTrainState(DEPARTURE);
                } else {
                    train.setTrainState(WAIT);
                }
                break;

            case DEPARTURE:
                // decrement occupation counter
                train.getFromStation().departure();
                // will also reset internal movement counter
                train.resetMoveCounter();
                train.setTrainState(MOVEMENT);
                break;

            case MOVEMENT:
                if (train.getNextPath().getLength() - train.getMoveCounter() > 0) {
                    train.move();
                } else {
                    train.setTrainState(ARRIVAL);
                }
                break;

            case ARRIVAL:
                train.resetMoveCounter();
                // remove reservation from the path
                pathService.freeReservation(train.getNextPath());
                // also set from an to station from calculated next path
                train.setNextPath(calculateNextPathForTrain(train));
                if (train.getDirection().equals(TrainDirect.FORWARD)) {
                    train.setFromStation(train.getNextPath().getF_node());
                    train.setToStation(train.getNextPath().getS_node());
                } else {
                    train.setFromStation(train.getNextPath().getS_node());
                    train.setToStation(train.getNextPath().getF_node());
                }
                train.setTrainState(STOP);
                break;
        }
        return train;
    }

    @Override
    public boolean hasFreePlace(Train train) {
        Long freePlaces = train.getCarriages().stream().map(Carriage::getCapacity).reduce(0L, Long::sum);
        return freePlaces > 0;
    }

    @Override
    public void reservePlace(Train train) {

    }

    @Override
    public Carriage getCarriageWithFreePlace(Train train) {
        for (Carriage carriage :
                train.getCarriages()) {
            if (carriage.getCapacity() > 0) {
                return carriage;
            }
        }
        return null;
    }

    @Override
    public boolean isAvailablePath(Train train, Station fromStation, Station toStation) {
        boolean isFromStationAvailable = false;
        boolean isToStationAvailable = false;
        int idx = train.getTrack().indexOf(train.getNextPath());
        switch (train.getDirection()) {
            case BACKWARD:
                for (int i = idx; i >= 0; i--) {
                    Path currentPath = train.getTrack().get(i);
                    if (currentPath.getF_node().equals(fromStation) || currentPath.getS_node().equals(toStation)) {
                        isFromStationAvailable = true;
                    }
                    if (currentPath.getF_node().equals(toStation) || currentPath.getS_node().equals(toStation)) {
                        isToStationAvailable = true;
                    }
                }
                break;

            case FORWARD:
                for (int i = 0; i < train.getTrack().size(); i++) {
                    Path currentPath = train.getTrack().get(i);
                    if (currentPath.getF_node().equals(toStation) || currentPath.getS_node().equals(toStation)) {
                        isToStationAvailable = true;
                    }
                    if (currentPath.getF_node().equals(fromStation) || currentPath.getS_node().equals(toStation)) {
                        isFromStationAvailable = true;
                    }
                }
                break;
        }
        return isFromStationAvailable && isToStationAvailable;
    }

    private Path calculateNextPathForTrain(Train train) {
        Path path = train.getNextPath();
        // TODO check for plying trains between only two station (For one path)
        if (train.getTrack().size() == 1) {
            if (train.getDirection().equals(TrainDirect.FORWARD)) {
                train.setDirection(TrainDirect.BACKWARD);
            } else {
                train.setDirection(TrainDirect.FORWARD);
            }
            return path;
        }

        int idx = train.getTrack().indexOf(path);

        if (idx == train.getTrack().size() - 1 && train.getDirection().equals(TrainDirect.FORWARD)) {
            train.setDirection(TrainDirect.BACKWARD);
            return train.getTrack().get(idx);
        }
        if (idx == 0 && train.getDirection().equals(TrainDirect.BACKWARD)) {
            train.setDirection(TrainDirect.FORWARD);
            return train.getTrack().get(idx);
        }
        if (train.getDirection().equals(TrainDirect.FORWARD)) {
            return train.getTrack().get(idx + 1);
        }
        if (train.getDirection().equals(TrainDirect.BACKWARD)) {
            return train.getTrack().get(idx - 1);
        }
        return path;
    }

    @Transactional
    @Override
    public void tick() {
        this.getActiveTrains().stream()
                .map(this::updateTrainState)
                .forEach(trainDAO::update);
    }

    public List<List<PathDTO>> getTrainsPath(Station from, Station to) {
        List<List<Station>> wayBetweenStations = pathService.findWayBetweenStations(from, to);
        List<List<Path>> paths = new ArrayList<>();

        for (List<Station> stations :
                wayBetweenStations) {
            List<Path> pathList = new ArrayList<>();
            for (int i = 0; i < stations.size() - 1; i++) {
                Station a = stations.get(i);
                Station b = stations.get(i + 1);
                pathList.add(pathService.getPathBetweenStation(a, b).get());
            }
            paths.add(pathList);
        }
        return paths.stream()
                .map(pths -> pths.stream()
                        .map(path ->
                                modelMapper
                                        .map(path, PathDTO.class)
                        ).collect(
                                Collectors
                                        .toList()
                        )
                )
                .collect(
                        Collectors
                                .toList()
                );
    }

    @Override
    public Train addTrain(TrainDTO trainDTO) {
        Train train = new Train();
        train.setNumber(trainDTO.getNumber());
        train.setTrainState(STOP);
        train.setDirection(TrainDirect.FORWARD);
        trainDAO.save(train);
        return train;
    }

    @Override
    public Train updateTrain(Train train, TrainDTO trainDTO) {
        train.setNumber(trainDTO.getNumber());
        trainDAO.update(train);
        return train;
    }

    @Override
    public List<Path> getAvailablePathsForTrain(Long trainId) {
        Train train = getById(trainId);
        return pathService.getAvailablePathsForTrain(train);
    }
}