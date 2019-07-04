package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Integer id);

    Optional<User> save(User user);

    Optional<User> update(Integer id, User user);

    void deleteById(Integer id);

    Optional<User> register(User user);
}
