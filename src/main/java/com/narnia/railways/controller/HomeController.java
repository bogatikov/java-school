package com.narnia.railways.controller;

import com.narnia.railways.model.Station;
import com.narnia.railways.model.Train;
import com.narnia.railways.service.SimulationServiceImpl;
import com.narnia.railways.service.StationServiceImpl;
import com.narnia.railways.service.TicketServiceImpl;
import com.narnia.railways.service.TrainService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.Objects;

@Controller
@RequestMapping("/")
class HomeController {

    private final StationServiceImpl stationServiceImpl;

    private final TrainService trainService;

    private final SimulationServiceImpl simulationService;

    HomeController(StationServiceImpl stationServiceImpl, TrainService trainService, SimulationServiceImpl simulationService) {
        this.stationServiceImpl = stationServiceImpl;
        this.trainService = trainService;
        this.simulationService = simulationService;
    }


    @GetMapping(value = "/")
    public ModelAndView getStationList() {
        ModelAndView modelAndView = new ModelAndView("stations");
        modelAndView.addObject("stations", stationServiceImpl.getAll());
        return modelAndView;
    }

    @GetMapping(value = "/tick")
    public String tick() {
        simulationService.tick();
        return "tick";
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


    @RequestMapping("/cal")
    public String cal() {
        Train train = trainService.getById(1L);
        System.out.println("===================-30");
        trainService.coordinateTrainStateWithTime(train, Instant.parse("2020-03-08T14:30:00Z"));
        System.out.println("===================00");
        trainService.coordinateTrainStateWithTime(train, Instant.parse("2020-03-08T15:00:00Z"));
        System.out.println("===================10");
        trainService.coordinateTrainStateWithTime(train, Instant.parse("2020-03-08T15:10:00Z"));
        System.out.println("===================11");
        trainService.coordinateTrainStateWithTime(train, Instant.parse("2020-03-08T15:11:00Z"));
        System.out.println("===================15");
        trainService.coordinateTrainStateWithTime(train, Instant.parse("2020-03-08T15:15:00Z"));
        System.out.println("===================20");
        trainService.coordinateTrainStateWithTime(train, Instant.parse("2020-03-08T15:20:00Z"));
        return "";
    }

    @GetMapping("/calculate")
    public String calculate(
            @RequestParam(name = "date") Instant date
    ) {
        Train train = trainService.getById(1L);
        trainService.coordinateTrainStateWithTime(train, date);
        trainService.update(train);
        return "";
    }

}