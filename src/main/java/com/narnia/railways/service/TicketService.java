package com.narnia.railways.service;

import com.narnia.railways.model.Ticket;
import com.narnia.railways.model.Train;
import com.narnia.railways.service.dto.BuyTicketDTO;
import com.narnia.railways.service.dto.PassengerDTO;
import com.narnia.railways.service.dto.TicketDTO;
import com.narnia.railways.service.dto.TicketReceiptDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TicketService {
    List<Ticket> getAll();

    void save(Ticket ticket);

    Ticket getById(Long id);

    void delete(Ticket ticket);

    void update(Ticket ticket);

    @Transactional
    Ticket buyTicketOnTheTrain(TicketDTO ticketDTO, PassengerDTO passengerDTO);

    @Transactional
    TicketReceiptDTO buyTicketOnTheTrain(BuyTicketDTO buyTicketDTO);

    void tick();

    List<Ticket> getActiveTickets();

    List<Ticket> getActiveTicketsForTrain(Train train);
}
