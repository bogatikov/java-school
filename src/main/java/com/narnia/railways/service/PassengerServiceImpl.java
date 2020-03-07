package com.narnia.railways.service;

import com.narnia.railways.dao.PassengerDAO;
import com.narnia.railways.model.Passenger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerServiceImpl {

    private final PassengerDAO passengerDAO;

    public PassengerServiceImpl(PassengerDAO passengerDAO) {
        this.passengerDAO = passengerDAO;
    }

    public List<Passenger> getAll() {
        return passengerDAO.list();
    }

    public void save(Passenger passenger) {
        passengerDAO.save(passenger);
    }

    public Passenger getById(Long id) {
        return passengerDAO.getById(id);
    }

    public void delete(Passenger passenger) {
        passengerDAO.delete(passenger);
    }

    public void update(Passenger passenger) {
        passengerDAO.update(passenger);
    }

}
