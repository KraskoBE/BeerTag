package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {

    Optional<Image> findById(Integer id);

    Optional<Image> save(MultipartFile file);

    Optional<Image> save(byte[] bytes);

    Optional<Image> update(Integer id, MultipartFile file);

    void deleteById(Integer id);
}
