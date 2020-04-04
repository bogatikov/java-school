package com.narnia.railways.service.dto;

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

    @NotNull(message = "The station value is required!")
    @Min(0)
    private Long val;

    @NotNull(message = "The station capacity is required!")
    @Min(0)
    private Integer capacity;
}
