package com.narnia.railways.controller;

import com.narnia.railways.model.Station;
import com.narnia.railways.service.StationServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("/")
class HomeController {

    private final StationServiceImpl stationServiceImpl;

    HomeController(StationServiceImpl stationServiceImpl) {
        this.stationServiceImpl = stationServiceImpl;
    }


    @GetMapping(value = "/")
    public ModelAndView getStationList() {
        ModelAndView modelAndView = new ModelAndView("stations");
        modelAndView.addObject("stations", stationServiceImpl.getAll());
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