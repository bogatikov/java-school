package com.narnia.railways.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

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

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Instant departureTime;
}
