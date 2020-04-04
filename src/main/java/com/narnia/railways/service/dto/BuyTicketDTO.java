package com.narnia.railways.service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
public class BuyTicketDTO {

    @NotNull
    private Long trainID;

    @NotNull
    private Long fromStationID;

    @NotNull
    private Long toStationID;

    @NotNull
    private String firsName;

    @NotNull
    private String lastName;

    @NotNull
    @DateTimeFormat(style = "yyyy.MM.dd")
    private Instant birthday;
}
