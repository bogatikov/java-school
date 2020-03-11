package com.narnia.railways.service;

import com.narnia.railways.dao.TrainDAO;
import com.narnia.railways.dao.TrainPathDAO;
import com.narnia.railways.model.Path;
import com.narnia.railways.model.Train;
import com.narnia.railways.model.TrainPath;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TrainServiceImpl implements TrainService {

    private final TrainDAO trainDAO;

    private final TrainPathDAO trainPathDAO;

    private Instant modelingTime;

    public TrainServiceImpl(TrainDAO trainDAO, TrainPathDAO trainPathDAO) {
        this.trainDAO = trainDAO;
        this.trainPathDAO = trainPathDAO;
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

    @Override
    public Train updateTrainState(Train train, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Instant date) {

        List<TrainPath> trainPaths = trainPathDAO.getSortedPathForTrain(train);
        if (date.compareTo(train.getDepartureTime()) < 0) {
            train.setCurrentState("The train is not departure yet.");
            return train;
        }

        if (date.compareTo(train.getDepartureTime()) == 0) {
            train.setCurrentState("The train departing right now!");
            return train;
        }

        Instant calc = Instant.from(train.getDepartureTime());

        for (TrainPath trainPath : trainPaths) {

            Path path = trainPath.getPath();

            // Add weight of line to current modeling time
            calc = calc.plus(path.getWeight(), ChronoUnit.MINUTES);

            // If date less or equal to current modeling time - the train on this line
            if (date.compareTo(calc) < 0) {
                train.setCurrentState("Train between "
                        + path.getF_node()
                        + " and "
                        + path.getS_node()
                );
                break;
            }

            // Add node weight to current modeling time
            calc = calc.plus(path.getS_node().getVal(), ChronoUnit.MINUTES);

            if (date.compareTo(calc) < 0) {
                train.setCurrentState("The train on the station: " + path.getS_node());
                train.setCurrentStation(path.getS_node());
                break;
            }
        }
        return train;
    }


    public void tick() {
        List<Train> trains = trainDAO.list();
        trains.stream().map(train -> updateTrainState(train, Instant.now())).forEach(trainDAO::save);
    }
}