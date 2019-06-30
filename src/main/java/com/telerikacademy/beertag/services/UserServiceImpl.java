package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("UserService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> save(User user) {
        Optional<User> oldUser = userRepository.findByEmail(user.getEmail());
        if (oldUser.isPresent())
            return Optional.empty();
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> update(Integer id, User user) {
        if (!findById(id).isPresent() || userRepository.findByEmail(user.getEmail()).isPresent())
            return Optional.empty();
        user.setId(id);
        return Optional.of(userRepository.save(user));
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}
