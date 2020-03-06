package com.narnia.railways.controller;

import com.narnia.railways.model.Station;
import com.narnia.railways.service.StationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("/")
class HomeController {

    private final StationService stationService;

    HomeController(StationService stationService) {
        this.stationService = stationService;
    }


    @GetMapping(value = "/")
    public ModelAndView getStationList() {
        ModelAndView modelAndView = new ModelAndView("stations");
        modelAndView.addObject("stations", stationService.getAll());
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

}