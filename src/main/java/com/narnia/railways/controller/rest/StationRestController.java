package com.narnia.railways.controller.rest;

import com.narnia.railways.model.Station;
import com.narnia.railways.service.StationService;
import com.narnia.railways.service.dto.StationDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
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
                        StationDTO::toDto
                )
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{station}")
    public ResponseEntity<StationDTO> getStation(@PathVariable(name = "station") Long stationId) {
        Station station = stationService.getById(stationId);
        return ResponseEntity.ok().body(StationDTO.toDto(station));
    }

    @GetMapping("/active")
    public ResponseEntity<List<StationDTO>> getActiveStationList() {
        List<Station> activeStations = stationService.getActiveStations();
        return ResponseEntity.ok().body(activeStations.stream()
                .map(StationDTO::toDto)
                .collect(Collectors.toList())
        );
    }

    @PostMapping
    public ResponseEntity<StationDTO> addStation(@RequestBody @Valid StationDTO stationDTO) {
        Station station = stationService.addStation(stationDTO);

        return ResponseEntity.ok().body(
                StationDTO.toDto(station)
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<StationDTO> updateStation(@PathVariable Long id, @RequestBody @Valid StationDTO stationDTO) {
        if (Objects.isNull(id)) {
            return ResponseEntity.notFound().build();
        }
        Station station = stationService.getById(id);
        if (Objects.isNull(station)) {
            return ResponseEntity.notFound().build();
        }

        Station updatedStation = stationService.updateStation(station, stationDTO);

        return ResponseEntity.ok().body(
                StationDTO.toDto(updatedStation)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStation(@PathVariable Long id) {
        if (Objects.isNull(id)) {
            return ResponseEntity.badRequest().build();
        }
        Station station = stationService.getById(id);
        if (Objects.isNull(station)) {
            return ResponseEntity.badRequest().build();
        }
        stationService.delete(station);
        return ResponseEntity.ok().build();
    }
}
