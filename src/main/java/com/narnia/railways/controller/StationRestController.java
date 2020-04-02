package com.narnia.railways.controller;

import com.narnia.railways.model.Station;
import com.narnia.railways.service.StationService;
import com.narnia.railways.service.dto.StationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/station")
@CrossOrigin
public class StationRestController {

    private final StationService stationService;

    private final ModelMapper modelMapper;

    public StationRestController(StationService stationService, ModelMapper modelMapper) {
        this.stationService = stationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<StationDTO>> getStationList() {
        List<Station> stations = stationService.getAll();
        return ResponseEntity.ok().body(stations.stream()
                .map(
                        station -> modelMapper.map(station, StationDTO.class)
                )
                .collect(Collectors.toList())
        );
    }
}
