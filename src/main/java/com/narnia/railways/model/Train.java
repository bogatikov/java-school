package com.narnia.railways.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Data
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String currentState;

    @ManyToOne
    private Station departure;

    @ManyToOne
    private Station arrival;

    @ManyToOne
    private Station currentStation;

    private Instant departureTime;
}
