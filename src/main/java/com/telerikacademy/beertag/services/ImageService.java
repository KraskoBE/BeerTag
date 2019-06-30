package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    List<Image> findAll();

    Optional<Image> findById(Integer id);

    Optional<Image> save(MultipartFile image);

    void deleteById(Integer id);
}
