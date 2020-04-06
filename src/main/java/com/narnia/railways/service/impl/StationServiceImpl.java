package com.narnia.railways.service.impl;

import com.narnia.railways.config.Constants;
import com.narnia.railways.dao.StationDAO;
import com.narnia.railways.model.Station;
import com.narnia.railways.service.StationService;
import com.narnia.railways.service.dto.StationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    private final StationDAO stationDAO;

    @Autowired
    private ModelMapper mapper;

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

    @Override
    public Station addStation(StationDTO stationDTO) {
        long awaitTime = (long)Math.ceil((double)stationDTO.getAwaitTime() / Constants.AMOUNT_OF_TIME_UNIT_PER_TICK);
        if (awaitTime == 0) {
            awaitTime = Constants.AMOUNT_OF_TIME_UNIT_PER_TICK;
        }


        Station station = new Station();
        station.setName(stationDTO.getName());
        station.setLongitude(stationDTO.getLongitude());
        station.setLatitude(stationDTO.getLatitude());
        station.setCapacity(stationDTO.getCapacity());
        station.setVal(awaitTime);

        this.save(station);
        return station;
    }

    @Override
    public Station updateStation(Station station, StationDTO stationDTO) {
        long awaitTime = (long)Math.ceil((double)stationDTO.getAwaitTime() / Constants.AMOUNT_OF_TIME_UNIT_PER_TICK);
        if (awaitTime == 0) {
            awaitTime = Constants.AMOUNT_OF_TIME_UNIT_PER_TICK;
        }


        station.setName(stationDTO.getName());
        station.setLongitude(stationDTO.getLongitude());
        station.setLatitude(stationDTO.getLatitude());
        station.setCapacity(stationDTO.getCapacity());
        station.setVal(awaitTime);
        stationDAO.update(station);
        return station;
    }
}
