package com.narnia.railways.service;

import java.time.Instant;

public interface Updatable {
    void setSimulationTime(Instant instant);
    void tick();
}
