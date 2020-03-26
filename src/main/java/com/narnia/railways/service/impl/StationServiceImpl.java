package com.narnia.railways.service.impl;

import com.narnia.railways.dao.StationDAO;
import com.narnia.railways.model.Station;
import com.narnia.railways.service.StationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    private final StationDAO stationDAO;

    public StationServiceImpl(StationDAO stationDAO) {
        this.stationDAO = stationDAO;
    }

    public List<Station> getAll() {
        return stationDAO.list();
    }

    public void save(Station station) {
        stationDAO.save(station);
    }

    public Station getById(Long id) {
        return stationDAO.getById(id);
    }

    public void delete(Station station) {
        stationDAO.delete(station);
    }

    public void update(Station station) {
        stationDAO.update(station);
    }


    public List<Station> getActiveStations() {
        return stationDAO.getActiveStationList();
    }
}
