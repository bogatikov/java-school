package com.narnia.railways.domain;

import lombok.Data;

import java.time.Instant;

@Data
public class Trip {
    private Long id;

    private Train train;
    private Instant date;

}
