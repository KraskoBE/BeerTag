package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Country;
import com.telerikacademy.beertag.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private Service<Country> countryService;

    @Autowired
    public CountryController(Service<Country> countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public List<Country> getAll() {
        return countryService.getAll();
    }

    @GetMapping("/{id}")
    public Country getById(@PathVariable int id) {
        return countryService.get(id);
    }

    @PostMapping
    public Country create(@RequestBody Country country) {
        try {
            return countryService.add(country);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Country update(@PathVariable int id, @RequestBody Country country) {
        try {
            return countryService.update(id, country);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable int id) {
        try {
            countryService.remove(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
