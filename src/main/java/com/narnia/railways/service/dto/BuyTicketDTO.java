package com.narnia.railways.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class BuyTicketDTO {

    @NotNull
    private Long trainID;

    @NotNull
    private Long fromStationID;

    @NotNull
    private Long toStationID;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthday;
}
