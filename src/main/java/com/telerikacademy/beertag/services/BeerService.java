package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.Image;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BeerService {

    List<Beer> findAll();

    Page<Beer> findBy(Integer pageNum, String orderBy);

    Optional<Beer> findById(Integer id);

    Optional<Beer> save(Integer creatorId, Beer beer, Image image);

    Optional<Beer> update(Integer id, Beer beer);

    void deleteById(Integer id);

    Optional<Beer> rate(Integer userId, Integer beerId, Integer rating);

    Optional<Beer> tag(Integer userId, Integer beerId, String tagName);
}
