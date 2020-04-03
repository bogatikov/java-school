package com.narnia.railways.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarriageDTO {
    private Long id;
    private Long capacity;
    private List<PassengerDTO> passengers;
}
