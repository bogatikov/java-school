package com.narnia.railways.controller.rest;

import com.narnia.railways.service.TicketService;
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

    @Autowired
    private ModelMapper modelMapper;

    public TicketRestController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<?> butTicket(@RequestBody @Valid BuyTicketDTO buyTicketDTO) {
        return ResponseEntity.ok().body(modelMapper.map(ticketService.buyTicketOnTheTrain(buyTicketDTO), TicketReceiptDTO.class));
    }
}
