package com.narnia.railways.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "train_state")
    private TrainState trainState;

    @Column(name = "train_direction")
    private TrainDirect direction = TrainDirect.FORWARD;

    @Column(name = "current_state")
    private String currentState;

    @ManyToOne
    private Station departure;

    @ManyToOne
    private Station arrival;

    @ManyToOne
    private Station currentStation;

    @OneToOne
    private Path currentPath;

    @Column(name = "departure_time")
    private Instant departureTime;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Path> track;
}
