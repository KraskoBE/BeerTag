package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    User getById(int id);
    User create(User user);
    User update(int id, User user);
    void delete(int id);

}
