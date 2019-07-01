package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Beer;

import java.util.List;
import java.util.Optional;

public interface BeerService {

    List<Beer> findAll();

    Optional<Beer> findById(Integer id);

    Optional<Beer> save(Integer creatorId, Beer beer);

    Optional<Beer> update(Integer id, Beer beer);

    void deleteById(Integer id);

    Optional<Beer> rate(Integer userId, Integer beerId, Integer rating);
}
