package com.example.conference_app_jpa.controllers;

import com.example.conference_app_jpa.models.TicketType;
import com.example.conference_app_jpa.repositories.TicketTypeJpaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ticket_type")
public class TicketTypeController {

    @Autowired
    private TicketTypeJpaRepository repository;

    @GetMapping
    public List<TicketType> list() {
        return repository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public TicketType get(@PathVariable String id) {
        return repository.getOne(id);
    }

    @PostMapping
    public TicketType create(@RequestBody final TicketType tp) {
        return repository.saveAndFlush(tp);
    }

    @DeleteMapping
    public void delete(@PathVariable String id) {
        repository.deleteById(id);
    }

    @PutMapping
    public TicketType update(@PathVariable String id, @RequestBody TicketType tp) {
        // because this is a PUT, we expect all attributes to be passed in. A PATCH
        // would only need what has changed.
        // TODO: Add validation that all attributes are passed in, otherwise return a
        // 400 bad payload
        TicketType existingTp = repository.getOne(id);
        BeanUtils.copyProperties(tp, existingTp, "ticket_price_id");
        return repository.saveAndFlush(tp);
    }

}
