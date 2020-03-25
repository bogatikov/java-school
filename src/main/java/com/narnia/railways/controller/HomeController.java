package com.narnia.railways.controller;

import com.narnia.railways.model.Station;
import com.narnia.railways.service.impl.SimulationServiceImpl;
import com.narnia.railways.service.impl.StationServiceImpl;
import com.narnia.railways.service.impl.TimeSimulationServiceImpl;
import com.narnia.railways.service.TrainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("/")
class HomeController {

    private final StationServiceImpl stationServiceImpl;

    private final TrainService trainService;

    private final SimulationServiceImpl simulationService;

    private final TimeSimulationServiceImpl timeSimulationServiceImpl;

    HomeController(StationServiceImpl stationServiceImpl, TrainService trainService, SimulationServiceImpl simulationService, TimeSimulationServiceImpl timeSimulationServiceImpl) {
        this.stationServiceImpl = stationServiceImpl;
        this.trainService = trainService;
        this.simulationService = simulationService;
        this.timeSimulationServiceImpl = timeSimulationServiceImpl;
    }


    @GetMapping(value = "/")
    public ModelAndView getStationList() {
        ModelAndView modelAndView = new ModelAndView("stations");
        modelAndView.addObject("stations", stationServiceImpl.getAll());
        return modelAndView;
    }

    @GetMapping(value = "/schedule")
    public ModelAndView getSchedule() {
        ModelAndView modelAndView = new ModelAndView("schedule");
        modelAndView.addObject("schedules", stationServiceImpl.getScheduleForStations());
        return modelAndView;
    }

    @GetMapping(value = "/tick")
    public ModelAndView tick() {
        ModelAndView modelAndView = new ModelAndView("tick");
        simulationService.tick();
        modelAndView.addObject("currentModelTime", timeSimulationServiceImpl.getCurrentSimulationTime());
        modelAndView.addObject("trains", trainService.getActiveTrains());
        modelAndView.addObject("schedules", stationServiceImpl.getScheduleForStations());
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView editCustomerForm(@RequestParam Long id) {
        ModelAndView mav = new ModelAndView("edit_station");
        Station station = stationServiceImpl.getById(id);
        mav.addObject("station", station);
        return mav;
    }

    @PostMapping(value = "/save")
    public String saveCustomer(@ModelAttribute("station") Station station) {
        if (Objects.nonNull(station.getId())) {
            stationServiceImpl.update(station);
        } else {
            stationServiceImpl.save(station);
        }
        return "redirect:/";
    }

    @RequestMapping("/delete")
    public String deleteStationForm(@RequestParam Long id) {
        Station station = stationServiceImpl.getById(id);
        if (Objects.nonNull(station))
            stationServiceImpl.delete(station);
        return "redirect:/";
    }

}