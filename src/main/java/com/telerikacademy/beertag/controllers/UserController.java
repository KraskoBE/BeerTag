package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable final int id) {
        return userService.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody final User user) {
        return userService.save(user)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable final int id, @RequestBody final User user) {
        return userService.update(id, user)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable final int id) {
        userService.deleteById(id);
    }

    /*@PutMapping("/{id}/rateBeer")
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
    }*/
}
