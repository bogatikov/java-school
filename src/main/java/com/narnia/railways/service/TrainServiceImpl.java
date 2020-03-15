package com.narnia.railways.service;

import com.narnia.railways.dao.TrainDAO;
import com.narnia.railways.model.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class TrainServiceImpl implements TrainService, Updatable {

    private final TrainDAO trainDAO;

    private Instant modelingTime;

    private final SimulationServiceImpl simulationService;

    private final StationServiceImpl stationService;

    private Map<Train, Long> tickCounter = new HashMap<>();

    public TrainServiceImpl(TrainDAO trainDAO, SimulationServiceImpl simulationService, StationServiceImpl stationService) {
        this.trainDAO = trainDAO;
        this.simulationService = simulationService;
        this.stationService = stationService;

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


    private void switchTrainDirection(Train train) {
        if (train.getDirection().equals(TrainDirect.FORWARD)) {
            train.setDirection(TrainDirect.BACKWARD);
            return;
        }
        train.setDirection(TrainDirect.FORWARD);
    }

    public Train updateTrainState(Train train) {
        tickCounter.put(train, tickCounter.get(train) + 1);
        if (train.getTrainState().equals(TrainState.STOP)) {
            Path path = getNextPath(train);
            if (Objects.isNull(path)) {
                return train;
            }
            Station to = train.getDirection().equals(TrainDirect.FORWARD)
                    ? path.getS_node() : path.getF_node();
            Station from = train.getDirection().equals(TrainDirect.FORWARD)
                    ? path.getF_node() : path.getS_node();
            if (tickCounter.get(train) >= from.getVal() && Objects.isNull(path.getTrain()) && to.getCapacity() > 0) {
                to.setCapacity(to.getCapacity() - 1);
                from.setCapacity(from.getCapacity() + 1);
                stationService.update(to);
                stationService.update(from);
                train.setTrainState(TrainState.MOVEMENT);
                train.setCurrentPath(path);
                train.setCurrentStation(null);
                tickCounter.put(train, 0L);
            } else {
                train.setTrainState(TrainState.WAIT);
            }
        }

        if (train.getTrainState().equals(TrainState.WAIT)) {
            train.setTrainState(TrainState.STOP);
        }

        if (train.getTrainState().equals(TrainState.MOVEMENT)) {
            if (train.getCurrentPath().getWeight().equals(tickCounter.get(train))) {
                train.setCurrentStation(train.getDirection() ==
                        TrainDirect.FORWARD ? train.getCurrentPath().getS_node() :
                        train.getCurrentPath().getF_node());

                if (train.getDirection().equals(TrainDirect.FORWARD)
                        &&
                        train.getCurrentStation().equals(train.getArrival())) {
                    switchTrainDirection(train);
                }
                if (train.getDirection().equals(TrainDirect.BACKWARD)
                        &&
                        train.getCurrentStation().equals(train.getDeparture())) {
                    switchTrainDirection(train);
                }
                train.setCurrentPath(null);
                train.setTrainState(TrainState.STOP);
                tickCounter.put(train, 0L);
            }
        }
        return train;
    }

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

        if (train.getTrainState().equals(TrainState.MOVEMENT)) {
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