package com.narnia.railways.service.impl;

import com.narnia.railways.dao.PathDAO;
import com.narnia.railways.model.Path;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathServiceImpl {

    private final PathDAO pathDAO;

    public PathServiceImpl(PathDAO pathDAO) {
        this.pathDAO = pathDAO;
    }

    public List<Path> getAll() {
        return pathDAO.list();
    }

    public void save(Path path) {
        pathDAO.save(path);
    }

    public Path getById(Long id) {
        return pathDAO.getById(id);
    }

    public void delete(Path path) {
        pathDAO.delete(path);
    }

    public void update(Path path) {
        pathDAO.update(path);
    }

    public void reserveFreeway(Path path) {
        path.reserveFreeway();
        pathDAO.update(path);
    }

    public void freeReservation(Path path) {
        path.freeReservation();
        pathDAO.update(path);
    }
}
