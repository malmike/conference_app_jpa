package com.example.conference_app_jpa.controllers;

import com.example.conference_app_jpa.models.TicketPrice;
import com.example.conference_app_jpa.repositories.TicketPriceJpaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket_prices")
public class TicketPriceController {

    @Autowired
    private TicketPriceJpaRepository repository;

    @GetMapping
    public List<TicketPrice> list() {
        return repository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public TicketPrice get(@PathVariable Long id) {
        return repository.getOne(id);
    }

    @PostMapping
    public TicketPrice create(@RequestBody final TicketPrice tp) {
        return repository.saveAndFlush(tp);
    }

    @DeleteMapping
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @PutMapping
    public TicketPrice update(@PathVariable Long id, @RequestBody TicketPrice tp) {
        // because this is a PUT, we expect all attributes to be passed in. A PATCH
        // would only need what has changed.
        // TODO: Add validation that all attributes are passed in, otherwise return a
        // 400 bad payload
        TicketPrice existingTp = repository.getOne(id);
        BeanUtils.copyProperties(tp, existingTp, "ticket_price_id");
        return repository.saveAndFlush(tp);
    }

}
