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

    @ManyToOne
    private Carriage carriage;

    @Column(name = "is_active")
    private Boolean active = true;

    @ManyToOne
    private Station fromStation;

    @ManyToOne
    private Station toStation;
}
