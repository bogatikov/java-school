package com.narnia.railways.service.impl;

import com.narnia.railways.service.Updatable;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TimeSimulationServiceImpl implements Updatable {

    @Getter
    private Instant currentSimulationTime;

    @Getter
    private Long amountOfTimeUnitPerTick = 5L;

    public TimeSimulationServiceImpl() {
        currentSimulationTime = Instant.parse("2020-03-08T15:20:00Z");
    }

    public Instant convertTicksToTime(long ticks) {
        if (ticks <= 0) {
            return Instant.ofEpochSecond(currentSimulationTime.getEpochSecond());
        }
        Instant result = Instant.ofEpochSecond(currentSimulationTime.getEpochSecond());
        result = result.plus(amountOfTimeUnitPerTick * ticks, ChronoUnit.MINUTES);
        return result;
    }

    @Override
    public void tick() {
        this.currentSimulationTime = this.currentSimulationTime.plus(amountOfTimeUnitPerTick, ChronoUnit.MINUTES);
    }
}