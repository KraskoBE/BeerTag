package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping
    public List<User> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable int id){
        try {
            return service.getById(id);
        } catch (IllegalArgumentException exc) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    exc.getMessage()
            );
        }
    }

    @PostMapping
    public User create(@Valid @RequestBody User user){
        try {
            return service.create(user);
        } catch (IllegalArgumentException exc) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    exc.getMessage()
            );
        }
    }

    @PutMapping("/{id}")
    public User update(@PathVariable int id, @RequestBody @Valid User user) {
        try {
            return service.update(id, user);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    ex.getMessage()
            );
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
           service.delete(id);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    ex.getMessage()
            );
        }
    }

}