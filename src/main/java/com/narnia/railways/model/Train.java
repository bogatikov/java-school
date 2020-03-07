package com.narnia.railways.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    @ManyToOne
    private Station departure;

    @ManyToOne
    private Station arrival;
}
