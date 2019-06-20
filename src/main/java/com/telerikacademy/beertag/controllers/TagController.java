package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Tag;
import com.telerikacademy.beertag.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private Service<Tag> tagService;

    @Autowired
    public TagController(Service<Tag> tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> getAll(){
        return tagService.getAll();
    }

    @GetMapping("/{id}")
    public Tag getById(@PathVariable int id) {
        return tagService.get(id);
    }

    @PostMapping
    public Tag create(@RequestBody Tag tag) {
        try {
            return tagService.add(tag);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Tag update(@PathVariable int id, @RequestBody Tag tag) {
        try {
            return tagService.update(id, tag);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable int id) {
        try {
            tagService.remove(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
