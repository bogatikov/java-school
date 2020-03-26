package com.narnia.railways.controller;

import com.narnia.railways.model.Station;
import com.narnia.railways.service.ScheduleService;
import com.narnia.railways.service.StationService;
import com.narnia.railways.service.TimeSimulationService;
import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.impl.PathServiceImpl;
import com.narnia.railways.service.impl.SimulationServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
class HomeController {

    private final StationService stationService;

    private final TrainService trainService;

    private final SimulationServiceImpl simulationService;

    private final TimeSimulationService timeSimulationService;

    private final PathServiceImpl pathService;

    private final ScheduleService scheduleService;

    HomeController(StationService stationService, TrainService trainService, SimulationServiceImpl simulationService, TimeSimulationService timeSimulationService, PathServiceImpl pathService, ScheduleService scheduleService) {
        this.stationService = stationService;
        this.trainService = trainService;
        this.simulationService = simulationService;
        this.timeSimulationService = timeSimulationService;
        this.pathService = pathService;
        this.scheduleService = scheduleService;
    }


    @GetMapping(value = "/")
    public ModelAndView getStationList() {
        ModelAndView modelAndView = new ModelAndView("stations");
        modelAndView.addObject("stations", stationService.getAll());
        return modelAndView;
    }

    @GetMapping(value = "/schedule")
    public ModelAndView getSchedule() {
        ModelAndView modelAndView = new ModelAndView("schedule");
        modelAndView.addObject("schedules", scheduleService.getScheduleForStations());
        return modelAndView;
    }

    @GetMapping(value = "/tick")
    public ModelAndView tick() {
        ModelAndView modelAndView = new ModelAndView("tick");
        simulationService.tick();
        modelAndView.addObject("currentModelTime", timeSimulationService.getCurrentSimulationTime());
        modelAndView.addObject("trains", trainService.getActiveTrains());
        modelAndView.addObject("schedules", scheduleService.getScheduleForStations());
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView editCustomerForm(@RequestParam Long id) {
        ModelAndView mav = new ModelAndView("edit_station");
        Station station = stationService.getById(id);
        mav.addObject("station", station);
        return mav;
    }

    @PostMapping(value = "/save")
    public String saveCustomer(@ModelAttribute("station") Station station) {
        if (Objects.nonNull(station.getId())) {
            stationService.update(station);
        } else {
            stationService.save(station);
        }
        return "redirect:/";
    }

    @RequestMapping("/delete")
    public String deleteStationForm(@RequestParam Long id) {
        Station station = stationService.getById(id);
        if (Objects.nonNull(station))
            stationService.delete(station);
        return "redirect:/";
    }

    @GetMapping("/test")
    public void dotest() {
        List<Station> activeStations = stationService.getActiveStations();

        pathService.printWay();
    }

}