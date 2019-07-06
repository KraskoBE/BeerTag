package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Image;
import com.telerikacademy.beertag.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service("ImageRepository")
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Optional<Image> findById(Integer id) {
        return imageRepository.findById(id);
    }

    @Override
    public Optional<Image> save(MultipartFile file) {
        if (isFileInvalid(file))
            return Optional.empty();
        try {
            return Optional.of(imageRepository.save(new Image(file.getBytes())));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Image> save(byte[] bytes) {
        return Optional.of(imageRepository.save(new Image(bytes)));
    }

    @Override
    public Optional<Image> update(Integer id, MultipartFile file) {
        if (isFileInvalid(file) || !imageRepository.findById(id).isPresent())
            return Optional.empty();
        try {
            return Optional.of(imageRepository.save(new Image(file.getBytes())));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Integer id) {
        imageRepository.deleteById(id);
    }

    private boolean isFileInvalid(MultipartFile file) {
        return !Objects.equals(file.getContentType(), "image/jpeg") &&
                !Objects.equals(file.getContentType(), "image/png");
    }
}
