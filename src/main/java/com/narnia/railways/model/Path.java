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

    @Column(name = "reserved")
    private Boolean reserved;

    @Column(name = "weight")
    private Long length;

    public boolean hasFreeway() {
        return !reserved;
    }

    public void reserveFreeway() {
        this.reserved = true;
    }

    public void freeReservation() {
        this.reserved = false;
    }
}
