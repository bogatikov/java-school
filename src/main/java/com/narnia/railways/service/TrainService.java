package com.narnia.railways.service;

import com.narnia.railways.model.Train;

import java.util.List;

public interface TrainService {

    List<Train> getAll();

    List<Train> getActiveTrains(); // Active trains have at least one path in their track;

    void save(Train train);

    Train getById(Long id);

    void delete(Train train);

    void update(Train train);
}
