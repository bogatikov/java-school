package com.narnia.railways.service.dto;

import lombok.Data;

@Data
public class TicketReceiptDTO {

    private TrainDTO train;
    private StationDTO from;
    private StationDTO to;
    private Boolean isActive;
    private PassengerDTO passenger;
    private CarriageTicketDTO carriage;
}
