package com.narnia.railways.service;

import com.narnia.railways.model.Station;
import com.narnia.railways.service.dto.StationDTO;

import java.util.List;

public interface StationService {

    List<Station> getAll();

    void save(Station station);

    Station getById(Long id);

    void delete(Station station);

    void update(Station station);

    List<Station> getActiveStations();

    Station addStation(StationDTO station);

    Station updateStation(Station station, StationDTO stationDTO);
}
