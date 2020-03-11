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

    @OneToMany(mappedBy = "currentStation", fetch = FetchType.EAGER)
    private List<Train> trains;
}
