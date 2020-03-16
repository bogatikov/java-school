package com.narnia.railways.service;

import com.narnia.railways.dao.TrainDAO;
import com.narnia.railways.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

import static com.narnia.railways.model.TrainState.*;

@Service
public class TrainServiceImpl implements TrainService, Updatable {

    private final TrainDAO trainDAO;

    private Instant modelingTime;

    private final SimulationServiceImpl simulationService;

    private final StationServiceImpl stationService;

    private final PathServiceImpl pathService;

    private Map<Train, Long> tickCounter = new HashMap<>();

    public TrainServiceImpl(TrainDAO trainDAO, SimulationServiceImpl simulationService, StationServiceImpl stationService, PathServiceImpl pathService) {
        this.trainDAO = trainDAO;
        this.simulationService = simulationService;
        this.stationService = stationService;
        this.pathService = pathService;

        simulationService.addToSimulation(this);
    }

    @Override
    public List<Train> getAll() {
        return trainDAO.list();
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
                train.setCurrentPath(train.getNextPath());
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
                // remove reservation from the path
//                train.getNextPath().freeReservation();
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

    private Path calculateNextPathForTrain(Train train) {
        Path path = train.getNextPath();
        switch (train.getDirection()) {
            case FORWARD:
                if (train.getNextPath().getS_node().equals(train.getArrival())) {
                    train.setDirection(TrainDirect.BACKWARD);
                } else {
                    path = train.getTrack().stream()
                            .filter(
                                    pth -> pth
                                            .getF_node()
                                            .equals(
                                                    train
                                                            .getNextPath().getS_node()
                                            )
                            )
                            .findFirst()
                            .orElseThrow();
                }
                break;
            case BACKWARD:
                if (train.getNextPath().getF_node().equals(train.getDeparture())) {
                    train.setDirection(TrainDirect.FORWARD);
                } else {
                    path = train.getTrack().stream()
                            .filter(
                                    pth -> pth
                                            .getS_node()
                                            .equals(
                                                    train
                                                            .getNextPath().getF_node()
                                            )
                            )
                            .findFirst()
                            .orElseThrow();
                }
                break;
        }
        return path;
    }

    @Transactional
    public void tick() {
        List<Train> trains = trainDAO.list();
        trains.forEach(train -> {
            tickCounter.putIfAbsent(train, 0L);
            System.out.println("Train " + train.getId() + " " + tickCounter.get(train));
        });
        trains.stream()
                .map(this::updateTrainState)
//                .map(train -> coordinateTrainStateWithTime(train, modelingTime))
                .forEach(trainDAO::update);
    }

    public Path getNextPath(Train train) {
        if (train.getTrack().size() == 0) {
            throw new RuntimeException("Train " + train.getId() + " doesn't have a track");
        }

        if (train.getTrainState().equals(MOVEMENT)) {
            return null;
        }
        Path path;
        if (train.getDirection().equals(TrainDirect.FORWARD)) {
            Optional<Path> first = train.getTrack().stream()
                    .filter(pth -> pth.getF_node().equals(train.getCurrentStation()))
                    .findFirst();
            path = first.orElseThrow();
        } else {
            Optional<Path> first = train.getTrack().stream()
                    .filter(pth -> pth.getS_node().equals(train.getCurrentStation()))
                    .findFirst();
            path = first.orElseThrow();
        }
        return path;
    }

    @Override
    public void setSimulationTime(Instant instant) {
        modelingTime = instant;
    }
}