package com.narnia.railways.service;

import com.narnia.railways.service.impl.SimulationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public interface Updatable {

    void tick();

    @Autowired
    default void registerMyself(SimulationServiceImpl simulationService) {
        simulationService.addToSimulation(this);
    }
}
