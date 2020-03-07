package com.narnia.railways.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Train train;

    @ManyToOne
    private Passenger passenger;

}
