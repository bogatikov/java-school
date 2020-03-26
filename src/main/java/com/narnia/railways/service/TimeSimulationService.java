package com.narnia.railways.service;

import java.time.Instant;

public interface TimeSimulationService {

    Instant convertTicksToTime(long ticks);

    Instant getCurrentSimulationTime();

    Long getAmountOfTimeUnitPerTick();
}
