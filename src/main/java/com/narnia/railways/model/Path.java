package com.narnia.railways.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Path {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Station f_node;

    @ManyToOne
    private Station s_node;

    private Long weight;
}
