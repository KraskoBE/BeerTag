package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.User;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository("UserRepository")
public class UserRepositoryImpl implements Repository<User> {
    private List<User> users;

    public UserRepositoryImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User add(User user) {
        users.add(user);
        return user;
    }

    @Override
    public User get(int id) {
        return users.stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("User with the id of %d does not exist", id)));
    }

    @Override
    public User update(User oldUser, User newUser) {
        oldUser.setAge(newUser.getAge());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setName(newUser.getName());
        oldUser.setPassword(oldUser.getPassword());
        return oldUser;
    }

    @Override
    public void remove(User user) {
        users.remove(user);
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}
