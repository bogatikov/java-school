package com.narnia.railways.domain;

import lombok.Data;

import java.time.Instant;

@Data
public class Passenger {
    private Long id;
    private String firstName;
    private String lastName;
    private Instant birthday;
}
