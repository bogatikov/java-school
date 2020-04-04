package com.narnia.railways.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainScheduleDTO {

    private TrainDTO train;
    private Long arriveThrough;
    private Instant arrivalTime;
}
