package com.narnia.railways.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class TrainBuyTicketDTO {
    private Long id;
    private String number;
    private StationDTO from;
    private StationDTO to;
    private List<PathDTO> track;
}
