package com.narnia.railways.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassengerDTO {

    private String firstName;
    private String lastName;
    private Instant birthday;
}
