package com.narnia.railways.service.dto;

import com.narnia.railways.model.Station;
import com.narnia.railways.model.Train;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

    private Train train;
    private Station toStation;
    private Station fromStation;
}
