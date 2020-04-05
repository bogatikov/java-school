package com.narnia.railways.controller.rest;

import com.narnia.railways.service.TicketService;
import com.narnia.railways.service.TrainService;
import com.narnia.railways.service.dto.BuyTicketDTO;
import com.narnia.railways.service.dto.TicketReceiptDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/ticket/")
@CrossOrigin
public class TicketRestController {

    private final TicketService ticketService;

    private final TrainService trainService;

    @Autowired
    private ModelMapper modelMapper;

    public TicketRestController(TicketService ticketService, TrainService trainService) {
        this.ticketService = ticketService;
        this.trainService = trainService;
    }


    @GetMapping("/{train}")
    public ResponseEntity<?> getAvailableStationsForTrain(@PathVariable(name = "train") Long trainId) {

        return ResponseEntity.ok().body(null);
    }

    @PostMapping
    public ResponseEntity<TicketReceiptDTO> butTicket(@RequestBody @Valid BuyTicketDTO buyTicketDTO) {
        return ResponseEntity.ok().body(modelMapper.map(ticketService.buyTicketOnTheTrain(buyTicketDTO), TicketReceiptDTO.class));
    }
}
