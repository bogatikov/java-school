package com.narnia.railways.service;

import com.narnia.railways.model.Path;
import com.narnia.railways.model.Station;
import com.narnia.railways.model.Train;

import java.util.List;
import java.util.Optional;

public interface PathService {
    List<Path> getAll();

    List<Path> getActivePaths();

    void save(Path path);

    Path getById(Long id);

    void delete(Path path);

    void update(Path path);

    Optional<Path> getPathBetweenStation(Station from, Station to);

    void reserveFreeway(Path path);

    void freeReservation(Path path);

    List<Path> getAvailablePathsForTrain(Train train);

    List<List<Station>> findWayBetweenStations(Station from, Station to);
}
