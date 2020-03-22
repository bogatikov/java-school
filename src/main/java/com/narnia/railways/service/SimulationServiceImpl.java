package com.narnia.railways.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimulationServiceImpl {

    private List<Updatable> simulatingEntities;

    public SimulationServiceImpl() {
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

        for (Updatable entity :
                simulatingEntities) {
            entity.tick();
        }
    }
}
