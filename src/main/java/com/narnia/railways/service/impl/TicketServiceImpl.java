package com.narnia.railways.service.impl;

import com.narnia.railways.controller.exception.BadRequestException;
import com.narnia.railways.dao.CarriageDAO;
import com.narnia.railways.dao.TicketDAO;
import com.narnia.railways.model.*;
import com.narnia.railways.service.TicketService;
import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.Updatable;
import com.narnia.railways.service.dto.BuyTicketDTO;
import com.narnia.railways.service.dto.PassengerDTO;
import com.narnia.railways.service.dto.TicketDTO;
import com.narnia.railways.service.dto.TicketReceiptDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService, Updatable {

    private final TicketDAO ticketDAO;

    private final CarriageDAO carriageDAO;

    private final TrainService trainService;

    private final StationServiceImpl stationService;

    private final PassengerServiceImpl passengerService;

    @Autowired
    private ModelMapper modelMapper;

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

        if (train.getNextPath().getS_node().equals(toStation) && train.getDirection().equals(TrainDirect.FORWARD)) {
            throw new BadRequestException("ticket", "The train is on the way to destination station");
        }
        if (train.getNextPath().getF_node().equals(toStation) && train.getDirection().equals(TrainDirect.BACKWARD)) {
            throw new BadRequestException("ticket", "The train is on the way to destination station");
        }
        if (fromStation.equals(toStation)) {
            throw new BadRequestException("ticket", "Stations from and to must be different");
        }
        if (!trainService.isAvailablePath(train, fromStation, toStation)) {
            throw new BadRequestException("ticket", "There is no paths between stations");
        }

        Optional<Carriage> retrievedCarriage = carriageDAO.getCarriageWithFreePlace(train);

        if (retrievedCarriage.isEmpty()) {
            throw new BadRequestException("ticket", "There is no vacations place on the train");
        }

        Carriage carriage = retrievedCarriage.get();
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
        ticket.setCarriage(carriage);
        ticketDAO.save(ticket);
        return ticket;
    }

    @Override
    @Transactional
    public TicketReceiptDTO buyTicketOnTheTrain(BuyTicketDTO buyTicketDTO) {

        Ticket ticket = buyTicketOnTheTrain(
                new TicketDTO(
                        trainService.getById(buyTicketDTO.getTrainID()),
                        stationService.getById(buyTicketDTO.getToStationID()),
                        stationService.getById(buyTicketDTO.getFromStationID())
                ),
                new PassengerDTO(
                        null,
                        buyTicketDTO.getFirstName(),
                        buyTicketDTO.getLastName(),
                        buyTicketDTO.getBirthday()
                )
        );
        return modelMapper.map(ticket, TicketReceiptDTO.class);
    }

    @Transactional
    @Override
    public void tick() {
        getActiveTickets()
                .forEach(ticket -> {
                    Train train = ticket.getTrain();
                    if (ticket.getToStation().equals(train.getFromStation()) && train.getTrainState().equals(TrainState.STOP)) {
                        ticket.setIsActive(false);
                        Carriage carriage = ticket.getCarriage();
                        carriage.setCapacity(ticket.getCarriage().getCapacity() + 1);
                        carriageDAO.update(carriage);
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
