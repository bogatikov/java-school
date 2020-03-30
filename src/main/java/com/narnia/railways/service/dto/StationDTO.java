package com.narnia.railways.service.dto;

import com.narnia.railways.model.Station;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationDTO {
    private String name;

    private Double longitude;

    private Double latitude;

    private Long val;

    private Integer capacity;

    public static StationDTO map(Station station) {
        return new StationDTO(station.getName(), station.getLongitude(), station.getLatitude(), station.getVal(), station.getCapacity());
    }
}
