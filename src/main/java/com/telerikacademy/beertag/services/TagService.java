package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    List<Tag> findAll();

    Optional<Tag> findById(Integer id);

    Optional<Tag> save(Tag tag);

    Optional<Tag> update(Integer id, Tag tag);

    void deleteById(Integer id);


}
