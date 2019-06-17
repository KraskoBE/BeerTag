package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Beer;

import java.util.List;

public interface BeerRepository {
    Beer add(Beer beer);

    Beer get(int id);

    void remove(Beer beer);

    List<Beer> getAll();

}
