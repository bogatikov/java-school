package com.narnia.railways.service;

import com.narnia.railways.model.Carriage;
import com.narnia.railways.model.Path;
import com.narnia.railways.model.Station;
import com.narnia.railways.model.Train;
import com.narnia.railways.service.dto.PathDTO;
import com.narnia.railways.service.dto.TrainDTO;

import java.util.List;

public interface TrainService {

    List<Train> getAll();

    List<Train> getActiveTrains(); // Active trains have at least one path in their track;

    void save(Train train);

    Train getById(Long id);

    void delete(Train train);

    void update(Train train);

    Train updateTrainState(Train train);

    boolean hasFreePlace(Train train);

    void reservePlace(Train train);

    Carriage getCarriageWithFreePlace(Train train);

    boolean isAvailablePath(Train train, Station fromStation, Station toStation);

    List<List<PathDTO>> getTrainsPath(Station from, Station to);

    Train addTrain(TrainDTO trainDTO);

    Train updateTrain(Train train, TrainDTO trainDTO);

    List<Path> getAvailablePathsForTrain(Long trainId);
}
