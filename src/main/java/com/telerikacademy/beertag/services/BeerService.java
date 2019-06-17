package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Beer;

import java.util.List;

public interface BeerService {
    Beer add(Beer beer);

    Beer get(int id);

    void remove(int id);

    List<Beer> getAll();
}
