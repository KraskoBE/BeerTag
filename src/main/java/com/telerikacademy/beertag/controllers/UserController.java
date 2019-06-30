package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.FullUserDTO;
import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.models.UserAuth;
import com.telerikacademy.beertag.repositories.UserAuthRepositoryImpl;
import com.telerikacademy.beertag.services.UserAuthServiceImpl;
import com.telerikacademy.beertag.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserServiceImpl userService;
    private UserAuthServiceImpl userAuthService;
    private UserAuthRepositoryImpl userAuthRepository;

    @Autowired
    public UserController(UserServiceImpl userService, UserAuthServiceImpl userAuthService, UserAuthRepositoryImpl userAuthRepository) {
        this.userService = userService;
        this.userAuthService = userAuthService;
        this.userAuthRepository = userAuthRepository;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/names")
    public List<String> getAllUserNames() {
        return userService.getAllUsernames();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id) {
        try {
            return userService.get(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage()
            );
        }
    }

    @PostMapping
    public User create(@RequestBody FullUserDTO user) {
        try {
            UserAuth userAuth = userAuthService.add(FullUserDTO.toUserAuth(user));
            userAuth.setUser(userService.add(FullUserDTO.toUser(user)));
            return userAuth.getUser();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    e.getMessage()
            );
        }
    }

    @PutMapping("/{id}")
    public User update(@PathVariable int id, @RequestBody User user) {
        try {
            return userService.update(id, user);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            userService.remove(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    @PutMapping("/{id}/rateBeer")
    public Beer rateBeer(@PathVariable(name = "id") int userId, @RequestParam int beerId, @RequestParam int rating) {
        if (rating < 0 || rating > 5)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rating should be between 0 and 5");

        try {
            return userService.rateBeer(userId, beerId, rating);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage()
            );

        }
    }

    @PutMapping("/{id}/wishBeer")
    public Beer wishBeer(@PathVariable(name = "id") int userId, @RequestParam int beerId) {
        try {
            return userService.addToWishList(userId, beerId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{id}/unWishBeer")
    public void unWishBeer(@PathVariable(name = "id") int userId, @RequestParam int beerId) {
        try {
            userService.removeFromWishList(userId, beerId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
