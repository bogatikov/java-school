package com.narnia.railways.service;

import com.narnia.railways.model.Station;
import com.narnia.railways.service.dto.TrainScheduleDTO;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

    Map<Station, List<TrainScheduleDTO>> getScheduleForStations();
}
