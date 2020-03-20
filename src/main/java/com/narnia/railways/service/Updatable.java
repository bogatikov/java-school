package com.narnia.railways.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

public interface Updatable {
    void setSimulationTime(Instant instant);
    void tick();

    @Autowired
    default void registerMyself(SimulationServiceImpl simulationService) {
        simulationService.addToSimulation(this);
    }
}
