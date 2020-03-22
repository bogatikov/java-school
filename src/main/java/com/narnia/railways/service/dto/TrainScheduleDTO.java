package com.narnia.railways.service.dto;

import com.narnia.railways.model.Train;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainScheduleDTO {

    private Train train;
    private Long arriveThrough;
    private Instant arrivalTime;
}
