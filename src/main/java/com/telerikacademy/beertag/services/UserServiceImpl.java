package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private Repository<User> userRepository;

    @Autowired
    public UserServiceImpl(UserRepository repo) {
        this.repository = repo;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User getById(int id) {
        return userRepository.getById();
    }

    @Override
    public User create(User user) {
        checkDuplicateId(user);

        checkDuplicateEmail(user);

        return userRepository.create(user);
    }

    @Override
    public User update(int id, User user) {
        checkDuplicateEmail(user);

        return userRepository.update(id, user);
    }

    @Override
    public User delete(int id) {
        return userRepository.delete(id);
    }
}
