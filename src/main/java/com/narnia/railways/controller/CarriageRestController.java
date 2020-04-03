package com.narnia.railways.controller;

import com.narnia.railways.service.CarriageService;
import com.narnia.railways.service.dto.CarriageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carriage/")
@CrossOrigin
public class CarriageRestController {

    private final CarriageService carriageService;

    public CarriageRestController(CarriageService carriageService) {
        this.carriageService = carriageService;
    }

    @GetMapping("/{train}")
    public ResponseEntity<List<CarriageDTO>> getCarriageListByTrainWithPassengers(@PathVariable(name = "train") Long trainId) {
        List<CarriageDTO> carriages = carriageService.getCarriageListWithPassenger(trainId);
        return ResponseEntity.ok().body(carriages);
    }
}
