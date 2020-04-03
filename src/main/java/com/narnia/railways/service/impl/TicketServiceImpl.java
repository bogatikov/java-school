package com.narnia.railways.service.impl;

import com.narnia.railways.dao.CarriageDAO;
import com.narnia.railways.dao.TicketDAO;
import com.narnia.railways.model.*;
import com.narnia.railways.service.TicketService;
import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.Updatable;
import com.narnia.railways.service.dto.BuyTicketDTO;
import com.narnia.railways.service.dto.PassengerDTO;
import com.narnia.railways.service.dto.TicketDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class TicketServiceImpl implements TicketService, Updatable {

    private final TicketDAO ticketDAO;

    private final CarriageDAO carriageDAO;

    private final TrainService trainService;

    private final StationServiceImpl stationService;

    private final PassengerServiceImpl passengerService;

    public TicketServiceImpl(TicketDAO ticketDAO, CarriageDAO carriageDAO, TrainService trainService, StationServiceImpl stationService, PassengerServiceImpl passengerService) {
        this.ticketDAO = ticketDAO;
        this.carriageDAO = carriageDAO;
        this.trainService = trainService;
        this.stationService = stationService;
        this.passengerService = passengerService;
    }

    @Override
    public List<Ticket> getAll() {
        return ticketDAO.list();
    }

    @Override
    public void save(Ticket ticket) {
        ticketDAO.save(ticket);
    }

    @Override
    public Ticket getById(Long id) {
        return ticketDAO.getById(id);
    }

    @Override
    public void delete(Ticket ticket) {
        ticketDAO.delete(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        ticketDAO.update(ticket);
    }

    @Override
    @Transactional
    public Ticket buyTicketOnTheTrain(TicketDTO ticketDTO, PassengerDTO passengerDTO) {
        final Station fromStation = ticketDTO.getFromStation();
        final Station toStation = ticketDTO.getToStation();
        final Train train = ticketDTO.getTrain();

        if (fromStation.equals(toStation)) {
            return null; //FIXME: return null is bad practice
        }
        if (!trainService.isAvailablePath(train, fromStation, toStation)) {
            return null;//FIXME: the same problem
        }

        Carriage carriage = carriageDAO.getCarriageWithFreePlace(train);

        if (Objects.isNull(carriage)) {
            //there is no free place on the train
            return null;
        }

        carriage.setCapacity(carriage.getCapacity() - 1);

        Passenger passenger = new Passenger();
        passenger.setFirstName(passengerDTO.getFirstName());
        passenger.setLastName(passengerDTO.getLastName());
        passenger.setBirthday(passengerDTO.getBirthday());
        passengerService.save(passenger);

        Ticket ticket = new Ticket();
        ticket.setFromStation(fromStation);
        ticket.setToStation(toStation);
        ticket.setTrain(train);
        ticket.setPassenger(passenger);
        ticketDAO.save(ticket);
        return ticket;
    }

    @Override
    @Transactional
    public Ticket buyTicketOnTheTrain(BuyTicketDTO buyTicketDTO) {

        return buyTicketOnTheTrain(
                new TicketDTO(
                        trainService.getById(buyTicketDTO.getTrainID()),
                        stationService.getById(buyTicketDTO.getFromStationID()),
                        stationService.getById(buyTicketDTO.getToStationID())
                ),
                new PassengerDTO(
                        buyTicketDTO.getFirsName(),
                        buyTicketDTO.getLastName(),
                        buyTicketDTO.getBirthday()
                )
        );
    }

    @Override
    public void tick() {
        getActiveTickets()
                .forEach(ticket -> {
                    Train train = ticket.getTrain();
                    if (ticket.getToStation().equals(train.getFromStation()) && train.getTrainState().equals(TrainState.STOP)) {
                        ticket.setActive(false);
                        ticketDAO.update(ticket);
                    }
                });
    }

    @Override
    public List<Ticket> getActiveTickets() {
        return ticketDAO.getActiveTicketList();
    }

    @Override
    public List<Ticket> getActiveTicketsForTrain(Train train) {
        return ticketDAO.getActiveTicketList(train);
    }
}
