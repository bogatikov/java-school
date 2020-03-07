package com.narnia.railways.service;

import com.narnia.railways.dao.TicketDAO;
import com.narnia.railways.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl {

    private final TicketDAO ticketDAO;

    public TicketServiceImpl(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public List<Ticket> getAll() {
        return ticketDAO.list();
    }

    public void save(Ticket ticket) {
        ticketDAO.save(ticket);
    }

    public Ticket getById(Long id) {
        return ticketDAO.getById(id);
    }

    public void delete(Ticket ticket) {
        ticketDAO.delete(ticket);
    }

    public void update(Ticket ticket) {
        ticketDAO.update(ticket);
    }
}
