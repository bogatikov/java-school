package com.narnia.railways.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id"})
public class Path {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Station f_node;

    @ManyToOne
    private Station s_node;

    private Long weight;

    @OneToOne(mappedBy = "currentPath")
    private Train train;
}
