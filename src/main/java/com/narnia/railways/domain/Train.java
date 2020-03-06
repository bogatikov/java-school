package com.narnia.railways.domain;

import com.narnia.railways.model.Station;
import lombok.Data;

@Data
public class Train {
    private Long id;
    private String number;
    private Station departure;
    private Station arrival;
}
