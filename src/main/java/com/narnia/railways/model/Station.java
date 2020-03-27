package com.narnia.railways.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString(of = {"id", "name"})
@EqualsAndHashCode(of = {"id"})
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;

    private Double longitude;

    private Double latitude;

    private Long val;

    @Column(name = "train_capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "toStation")
    private List<Train> arrivingTrains;

    @OneToMany(mappedBy = "fromStation")
    private List<Train> departingTrains;

    public boolean hasFreePlatform() {
        return this.capacity > 0;
    }

    public void departure() {
        this.capacity = this.capacity + 1;
    }

    public void reservePlatform() {
        this.capacity--;
    }
}
