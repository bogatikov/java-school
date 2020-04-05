package com.narnia.railways.controller.rest;

import com.narnia.railways.model.Train;
import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.dto.TrainDTO;
import com.narnia.railways.service.dto.TrainBuyTicketDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/train")
@CrossOrigin
public class TrainRestController {

    private final TrainService trainService;

    private final ModelMapper modelMapper;

    public TrainRestController(TrainService trainService, ModelMapper modelMapper) {
        this.trainService = trainService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TrainDTO>> getTrainList() {
        List<Train> trains = trainService.getAll();
        return ResponseEntity.ok().body(trains.stream()
                .map(
                        train -> modelMapper.map(train, TrainDTO.class)
                )
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/{train}")
    public ResponseEntity<TrainDTO> getTrain(@PathVariable(name = "train") Long trainId) {
        Train train = trainService.getById(trainId);
        return ResponseEntity.ok().body(modelMapper.map(train, TrainDTO.class));
    }

    @GetMapping("/track/{train}")
    public ResponseEntity<TrainBuyTicketDTO> getTrainTrack(@PathVariable(name = "train") Long trainId) {

        Train train = trainService.getById(trainId);
        return ResponseEntity.ok().body(modelMapper.map(train, TrainBuyTicketDTO.class));
    }

    @PostMapping
    public ResponseEntity<TrainDTO> addTrain(@RequestBody @Valid TrainDTO trainDTO) {
        Train train = trainService.addTrain(trainDTO);

        return ResponseEntity.ok().body(
                modelMapper.map(train, TrainDTO.class)
        );
    }

    @PostMapping("/{id}")
    public ResponseEntity<TrainDTO> updateStation(@PathVariable Long id, @RequestBody @Valid TrainDTO trainDTO) {
        if (Objects.isNull(id)) {
            return ResponseEntity.notFound().build();
        }
        Train train = trainService.getById(id);
        if (Objects.isNull(train)) {
            return ResponseEntity.notFound().build();
        }

        Train updatedTrain = trainService.updateTrain(train, trainDTO);

        return ResponseEntity.ok().body(
                modelMapper.map(updatedTrain, TrainDTO.class)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStation(@PathVariable Long id) {
        if (Objects.isNull(id)) {
            return ResponseEntity.badRequest().build();
        }
        Train train = trainService.getById(id);
        if (Objects.isNull(train)) {
            return ResponseEntity.badRequest().build();
        }
        trainService.delete(train);
        return ResponseEntity.ok().build();
    }
}
