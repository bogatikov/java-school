package com.narnia.railways.controller;

import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.dto.TicketDTO;
import com.narnia.railways.service.impl.TicketServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ticket/")
public class TicketController {

    private final TrainService trainService;

    private final TicketServiceImpl ticketService;

    public TicketController(TrainService trainService, TicketServiceImpl ticketService) {
        this.trainService = trainService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public ModelAndView trains() {
        ModelAndView modelAndView = new ModelAndView("ticket/main");
        modelAndView.addObject("trains", trainService.getActiveTrains());
        modelAndView.addObject("ticket", new TicketDTO());
        return modelAndView;
    }


}
