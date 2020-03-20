package com.narnia.railways.service;

import com.narnia.railways.dao.TrainDAO;
import com.narnia.railways.model.Path;
import com.narnia.railways.model.Train;
import com.narnia.railways.model.TrainDirect;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.narnia.railways.model.TrainState.*;

@Service
public class TrainServiceImpl implements TrainService, Updatable {

    private final TrainDAO trainDAO;

    private Instant modelingTime;

    private final PathServiceImpl pathService;

    private Map<Train, Long> tickCounter = new HashMap<>();

    public TrainServiceImpl(TrainDAO trainDAO, PathServiceImpl pathService) {
        this.trainDAO = trainDAO;
        this.pathService = pathService;
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

    private Path calculateNextPathForTrain(Train train) {
        Path path = train.getNextPath();
        // TODO check for plying trains between only two station (For one path)
        if (train.getTrack().size() <= 1)
            return path;

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
        List<Train> trains = trainDAO.list();
        trains.stream()
                .map(this::updateTrainState)
                .forEach(trainDAO::update);
    }

    @Override
    public void setSimulationTime(Instant instant) {
        modelingTime = instant;
    }
}