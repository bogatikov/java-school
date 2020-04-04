package com.narnia.railways.controller.rest;

import com.narnia.railways.service.ScheduleService;
import com.narnia.railways.service.dto.StationDTO;
import com.narnia.railways.service.dto.TrainScheduleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/schedule")
@CrossOrigin
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    public ScheduleRestController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public ResponseEntity<Map<Long, List<TrainScheduleDTO>>> getTrainsSchedule() {
        Map<Long, List<TrainScheduleDTO>> scheduleForStations = scheduleService.getScheduleForStations();
        return ResponseEntity.ok().body(scheduleForStations);
    }
}
