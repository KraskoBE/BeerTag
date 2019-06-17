package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {
    private Repository<User> userRepository;

    @Autowired
    public UserServiceImpl(Repository<User> repo) {
        this.userRepository = repo;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User getById(int id) {
        return userRepository.get(id);
    }

    @Override
    public User create(User user) {
        checkDuplicateId(user);

        checkDuplicateEmail(user);

        return userRepository.add(user);
    }

    @Override
    public User update(int id, User user) {
        checkDuplicateEmail(user);

        return userRepository.update(userRepository.get(id), user);
    }

    @Override
    public void delete(int id) {
        userRepository.remove(userRepository.get(id));
    }

    private void checkDuplicateEmail(User user) {
         List<User> duplicateEmails = userRepository.getAll()
                 .stream()
                 .filter(x -> x.getEmail().equals(user.getEmail()))
                 .collect(Collectors.toList());

         if(duplicateEmails.size() > 0) {
             throw new RuntimeException(String.format("User with the email %s already exists!", user.getEmail()));
         }
    }

    private void checkDuplicateId(User user) {
        List<User> duplicateIds = userRepository.getAll().stream()
                .filter(x -> x.getId() == user.getId())
                .collect(Collectors.toList());

        if(duplicateIds.size() > 0) {
            throw new RuntimeException(String.format("User with the id of %d already exists", user.getId()));
        }

    }

}
