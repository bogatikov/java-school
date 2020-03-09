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
public class TrainServiceImpl {

    private final TrainDAO trainDAO;

    private final TrainPathDAO trainPathDAO;

    public TrainServiceImpl(TrainDAO trainDAO, TrainPathDAO trainPathDAO) {
        this.trainDAO = trainDAO;
        this.trainPathDAO = trainPathDAO;
    }

    public List<Train> getAll() {
        return trainDAO.list();
    }

    public void save(Train train) {
        trainDAO.save(train);
    }

    public Train getById(Long id) {
        return trainDAO.getById(id);
    }

    public void delete(Train train) {
        trainDAO.delete(train);
    }

    public void update(Train train) {
        trainDAO.update(train);
    }

    public void calculateTrainPosition(Train train, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Instant date) {

        List<TrainPath> trainPaths = trainPathDAO.getSortedPathForTrain(train);
        System.out.println("Date: " + date.getEpochSecond());
        System.out.println("Departure: " + train.getDepartureTime().getEpochSecond());
        if (date.compareTo(train.getDepartureTime()) < 0) {
            System.out.println("The train is not departure yet.");
            // add an exception?
            return;
        }

        if (date.compareTo(train.getDepartureTime()) == 0) {
            System.out.println("The train departing right now!");
            return;
        }

        Instant calc = Instant.from(train.getDepartureTime());

        for (int i = 0; i < trainPaths.size(); i++) {

            Path path = trainPaths.get(i).getPath();

            // Add weight of line to current modeling time
            calc = calc.plus(path.getWeight(), ChronoUnit.MINUTES);

            // If date less or equal to current modeling time - the train on this line
            if (date.compareTo(calc) < 0) {
                System.out.println("Train between "
                        + path.getF_node()
                        + " and "
                        + path.getS_node()
                );
                break;
            }

            // Add node weight to current modeling time
            calc = calc.plus(path.getS_node().getVal(), ChronoUnit.MINUTES);

            if (date.compareTo(calc) < 0) {
                System.out.println("The train on the station: " + path.getS_node());
                break;
            }
        }

    }
}
