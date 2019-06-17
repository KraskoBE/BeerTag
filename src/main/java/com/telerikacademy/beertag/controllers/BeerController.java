package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.services.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/beers")
public class BeerController {

    private BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping
    public List<Beer> getAll() {
        return beerService.getAll();
    }

    @GetMapping("/{id}")
    public Beer getById(@PathVariable int id) {
        return beerService.get(id);
    }

    @PostMapping
    public Beer create(@RequestBody Beer beer) {
        try {
            return beerService.add(beer);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable int id)
    {
        try {
            beerService.remove(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

}
