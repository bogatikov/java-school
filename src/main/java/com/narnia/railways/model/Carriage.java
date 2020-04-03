package com.narnia.railways.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Carriage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_sits")
    private Long capacity;

    @ManyToOne
    private Train train;
}
