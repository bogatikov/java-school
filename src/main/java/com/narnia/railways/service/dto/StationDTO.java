package com.narnia.railways.service.dto;

import com.narnia.railways.config.Constants;
import com.narnia.railways.model.Station;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class StationDTO {

    private Long id;

    @NotBlank(message = "The station name is required!")
    @Size(min = 1, max = 255)
    private String name;

    @NotNull(message = "The station longitude coordinate is required!")
    private Double longitude;

    @NotNull(message = "The station latitude coordinate is required!")
    private Double latitude;

    @NotNull(message = "The station awaitTime is required!")
    @Min(1)
    private Long awaitTime;

    @NotNull(message = "The station capacity is required!")
    @Min(0)
    private Integer capacity;

    public static StationDTO toDto(Station station) {
        StationDTO dto = new StationDTO();
        dto.setId(station.getId());
        dto.setCapacity(station.getCapacity());
        dto.setLatitude(station.getLatitude());
        dto.setLongitude(station.getLongitude());
        dto.setName(station.getName());
        dto.setAwaitTime(station.getVal() * Constants.AMOUNT_OF_TIME_UNIT_PER_TICK);
        return dto;
    }

    public static Station fromDto(StationDTO dto) {
        Station station = new Station();
        station.setId(dto.getId());
        station.setCapacity(dto.getCapacity());
        station.setLatitude(dto.getLatitude());
        station.setLongitude(dto.getLongitude());
        station.setName(dto.getName());
        station.setVal((long) Math.ceil((double)dto.getAwaitTime() / Constants.AMOUNT_OF_TIME_UNIT_PER_TICK));
        return station;
    }
}
