package com.narnia.railways.service;

import com.narnia.railways.model.Train;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.List;

public interface TrainService {

    List<Train> getAll();

    void save(Train train);

    Train getById(Long id);

    void delete(Train train);

    void update(Train train);

    Train coordinateTrainStateWithTime(Train train, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Instant date);
}
