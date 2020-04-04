package com.narnia.railways.service.impl;

import com.narnia.railways.dao.CarriageDAO;
import com.narnia.railways.model.Carriage;
import com.narnia.railways.model.Passenger;
import com.narnia.railways.model.Ticket;
import com.narnia.railways.model.Train;
import com.narnia.railways.service.CarriageService;
import com.narnia.railways.service.TicketService;
import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.dto.CarriageDTO;
import com.narnia.railways.service.dto.PassengerDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarriageServiceImpl implements CarriageService {

    private final CarriageDAO carriageDAO;

    private final TrainService trainService;

    private final TicketService ticketService;

    public CarriageServiceImpl(CarriageDAO carriageDAO, TrainService trainService, TicketService ticketService) {
        this.carriageDAO = carriageDAO;
        this.trainService = trainService;
        this.ticketService = ticketService;
    }

    @Override
    public List<Carriage> getCarriageListByTrain(Long trainId) {
        Train train = trainService.getById(trainId);
        return carriageDAO.getCarriagesByTrain(train);
    }

    @Override
    public List<CarriageDTO> getCarriageListWithPassenger(Long trainId) {
        Train train = trainService.getById(trainId);
        List<Ticket> activeTicketsForTrain = ticketService.getActiveTicketsForTrain(train);
        Map<Carriage, List<Passenger>> carriagePassengers = new HashMap<>();
        activeTicketsForTrain.forEach(ticket -> {
            carriagePassengers.putIfAbsent(ticket.getCarriage(), new ArrayList<>());
            carriagePassengers.get(ticket.getCarriage()).add(ticket.getPassenger());
        });

        return carriagePassengers.keySet().stream().map(carriage -> {
            CarriageDTO carriageDTO = new CarriageDTO();
            carriageDTO.setId(carriage.getId());
            carriageDTO.setCapacity(carriage.getCapacity());
            carriageDTO.setPassengers(
                    carriagePassengers.get(carriage).stream().map(passenger -> {
                        PassengerDTO passengerDTO = new PassengerDTO();
                        passengerDTO.setFirstName(passenger.getFirstName());
                        passengerDTO.setLastName(passenger.getLastName());
                        passengerDTO.setBirthday(passenger.getBirthday());
                        return passengerDTO;
                    }).collect(Collectors.toList())
            );
            return carriageDTO;
        }).collect(Collectors.toList());
    }
}

