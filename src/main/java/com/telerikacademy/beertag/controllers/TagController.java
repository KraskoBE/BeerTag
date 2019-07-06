package com.telerikacademy.beertag.controllers;

import com.telerikacademy.beertag.models.Tag;
import com.telerikacademy.beertag.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @PostMapping
    public ResponseEntity<Tag> save(@RequestBody final Tag tag) {
        return tagService.save(tag)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.badRequest().build());
    }
}
