package com.narnia.railways.service.dto;

import com.narnia.railways.model.TrainDirect;
import com.narnia.railways.model.TrainState;
import lombok.Data;

@Data
public class TrainDTO {
    private String number;
    private StationDTO from;
    private StationDTO to;
    private TrainState trainState;
    private TrainDirect direction;
}
