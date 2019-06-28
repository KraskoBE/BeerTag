package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.UserAuth;
import com.telerikacademy.beertag.repositories.UserAuthRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("UserAuthService")
public class UserAuthServiceImpl implements com.telerikacademy.beertag.services.Service<UserAuth, Integer> {
    private UserAuthRepositoryImpl userAuthRepository;

    @Autowired
    public UserAuthServiceImpl(UserAuthRepositoryImpl userAuthRepository) {
        this.userAuthRepository = userAuthRepository;
    }

    @Override
    public UserAuth add(UserAuth userAuth) {
        List<UserAuth> users = getAll().stream()
                .filter(x -> x.getEmail().equals(userAuth.getEmail()))
                .collect(Collectors.toList());

        if (users.size() > 0) {
            throw new IllegalArgumentException("User with email already exists");
        }
        return userAuthRepository.add(userAuth);
    }

    @Override
    public UserAuth get(Integer id) {
        UserAuth userAuth = userAuthRepository.get(id);
        if (userAuth == null)
            throw new IllegalArgumentException(String.format("User with id %d not found.", id));
        return userAuth;
    }

    @Override
    public UserAuth update(Integer id, UserAuth newUserAuth) {
        UserAuth oldUserAuth = get(id);
        return userAuthRepository.update(oldUserAuth, newUserAuth);
    }

    @Override
    public void remove(Integer id) {
        userAuthRepository.remove(get(id));
    }

    @Override
    public List<UserAuth> getAll() {
        return userAuthRepository.getAll();
    }
}
