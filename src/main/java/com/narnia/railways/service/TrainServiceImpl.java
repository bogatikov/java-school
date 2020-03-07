package com.narnia.railways.service;

import com.narnia.railways.dao.TrainDAO;
import com.narnia.railways.model.Train;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainServiceImpl {

    private final TrainDAO trainDAO;

    public TrainServiceImpl(TrainDAO trainDAO) {
        this.trainDAO = trainDAO;
    }

    public List<Train> getAll() {
        return trainDAO.list();
    }

    public void save(Train train) {
        trainDAO.save(train);
    }

    public Train getById(Long id) {
        return trainDAO.getById(id);
    }

    public void delete(Train train) {
        trainDAO.delete(train);
    }

    public void update(Train train) {
        trainDAO.update(train);
    }
}
