package com.narnia.railways.controller.rest;

import com.narnia.railways.model.Path;
import com.narnia.railways.model.Station;
import com.narnia.railways.service.PathService;
import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.dto.PathDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/path")
@CrossOrigin
public class PathRestController {

    private final PathService pathService;

    private final TrainService trainService;

    private final ModelMapper modelMapper;

    public PathRestController(PathService pathService, TrainService trainService, ModelMapper modelMapper) {
        this.pathService = pathService;
        this.trainService = trainService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<PathDTO>> getPathList() {
        return ResponseEntity.ok(pathService.getAll().stream()
                .map(path -> modelMapper.map(path, PathDTO.class))
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/find/from/{from}/to/{to}")
    public ResponseEntity<?> findWayBetweenStations(@PathVariable(name = "from") Long fromId, @PathVariable(name = "to") Long toId) {

        List<List<PathDTO>> trainsPath = trainService.getTrainsPath(fromId, toId);
        return ResponseEntity.ok().body(trainsPath);
    }

    @GetMapping("/{train}")
    public ResponseEntity<List<PathDTO>> getAvailablePathForTrain(
            @PathVariable(name = "train") Long trainId
    ) {
        List<Path> paths = trainService.getAvailablePathsForTrain(trainId);
        return ResponseEntity.ok().body(paths.stream()
                .map(path -> modelMapper.map(path, PathDTO.class))
                .collect(Collectors.toList())
        );
    }
}
