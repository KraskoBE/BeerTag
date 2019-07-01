package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.services.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/beers")
public class BeerController {
    private final BeerService beerService;

    @Autowired
    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping
    public List<Beer> findAll() {
        return beerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Beer> findById(@PathVariable final int id) {
        return beerService.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Beer> save(@RequestParam(name = "creator") final int creatorId, @RequestBody final Beer beer) {
        return beerService.save(creatorId, beer)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Beer> update(@PathVariable final int id, @RequestBody final Beer beer) {
        return beerService.update(id, beer)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final int id) {
        beerService.deleteById(id);
    }
}
