package com.narnia.railways.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationServiceImpl {

    private Instant currentSimulationTime;

    private List<Updatable> simulatingEntities;

    public SimulationServiceImpl() {
        currentSimulationTime = Instant.parse("2020-03-08T15:20:00Z");
        simulatingEntities = new ArrayList<>();
    }

    public void addToSimulation(Updatable updatable) {
        simulatingEntities.add(updatable);
    }

    public void removeFromSimulation(Updatable updatable) {
        simulatingEntities.remove(updatable);
    }

//    @Scheduled(fixedDelay = 60_000L)
    public void tick() {
        this.currentSimulationTime = this.currentSimulationTime.plus(5, ChronoUnit.MINUTES);
        System.out.println("Current simulation time is " + currentSimulationTime);
        for (Updatable entity:
             simulatingEntities) {
            entity.setSimulationTime(currentSimulationTime);
            entity.tick();
        }
    }
}
