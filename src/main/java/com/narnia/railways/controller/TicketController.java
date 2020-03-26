package com.narnia.railways.controller;

import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.dto.SearchDTO;
import com.narnia.railways.service.dto.TicketDTO;
import com.narnia.railways.service.impl.StationServiceImpl;
import com.narnia.railways.service.impl.TicketServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ticket/")
public class TicketController {

    private final TrainService trainService;

    private final TicketServiceImpl ticketService;

    private final StationServiceImpl stationService;

    public TicketController(TrainService trainService, TicketServiceImpl ticketService, StationServiceImpl stationService) {
        this.trainService = trainService;
        this.ticketService = ticketService;
        this.stationService = stationService;
    }

    @GetMapping
    public ModelAndView trains() {
        ModelAndView modelAndView = new ModelAndView("ticket/main");
        modelAndView.addObject("trains", trainService.getActiveTrains());
        modelAndView.addObject("ticket", new TicketDTO());
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search() {
        ModelAndView modelAndView = new ModelAndView("ticket/search");
        modelAndView.addObject("stations", stationService.getAll());
        modelAndView.addObject("search", new SearchDTO());
        return modelAndView;
    }

    @PostMapping("/search")
    public ModelAndView doSearch(@RequestParam(value = "from") Long from, @RequestParam(value = "to") Long to) {
        ModelAndView modelAndView = new ModelAndView("ticket/searchResult");

        return modelAndView;
    }
}
