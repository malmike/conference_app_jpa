package com.example.conference_app_jpa.controllers;

import com.example.conference_app_jpa.models.PricingCategory;
import com.example.conference_app_jpa.repositories.PricingCategoryJpaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pricing_categories")
public class PricingCategoryController {

    @Autowired
    private PricingCategoryJpaRepository repository;

    @GetMapping
    public List<PricingCategory> list() {
        return repository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public PricingCategory get(@PathVariable String id) {
        return repository.getOne(id);
    }

    @PostMapping
    public PricingCategory create(@RequestBody final PricingCategory tp) {
        return repository.saveAndFlush(tp);
    }

    @DeleteMapping
    public void delete(@PathVariable String id) {
        repository.deleteById(id);
    }

    @PutMapping
    public PricingCategory update(@PathVariable String id, @RequestBody PricingCategory tp) {
        // because this is a PUT, we expect all attributes to be passed in. A PATCH
        // would only need what has changed.
        // TODO: Add validation that all attributes are passed in, otherwise return a
        // 400 bad payload
        PricingCategory pricingCategory = repository.getOne(id);
        BeanUtils.copyProperties(tp, pricingCategory, "ticket_price_id");
        return repository.saveAndFlush(tp);
    }

}
