package com.narnia.railways.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString(of = {"id", "name", "longitude", "latitude"})
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

    @OneToMany(mappedBy = "currentStation")
    private List<Train> trains;

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
