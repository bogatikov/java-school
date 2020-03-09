package com.narnia.railways.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TrainPath {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @ManyToOne
    @JoinColumn(name = "path_id")
    private Path path;

    private Long path_order;
}
