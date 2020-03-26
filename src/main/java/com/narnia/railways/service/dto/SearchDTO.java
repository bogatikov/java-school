package com.narnia.railways.service.dto;

import com.narnia.railways.model.Station;
import lombok.Data;

@Data
public class SearchDTO {

    private Station from;
    private Station to;
}
