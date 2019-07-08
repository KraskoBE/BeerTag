package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.DTO.UserBeerDetails;
import com.telerikacademy.beertag.models.DTO.UserUpdateDTO;
import com.telerikacademy.beertag.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Integer id);

    Optional<User> save(User user);

    Optional<User> update(Integer id, UserUpdateDTO user);

    void deleteById(Integer id);

    Optional<User> register(User user);

    Optional<User> wish(Integer userId, Integer beerId);

    Optional<User> drink(Integer userId, Integer beerId);

    Optional<UserBeerDetails> beerDetails(Integer userId, Integer beerId);

    List<Beer> findWishedBeers(Integer userId);

    List<Beer> findCreatedBeers(Integer userId);

    List<Beer> findTopDrank(Integer userId);
}
