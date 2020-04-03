package com.narnia.railways.service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;

@Data
public class BuyTicketDTO {

    private Long trainID;
    private Long fromStationID;
    private Long toStationID;

    private String firsName;
    private String lastName;

    @DateTimeFormat(style = "yyyy.MM.dd")
    private Instant birthday;
}
