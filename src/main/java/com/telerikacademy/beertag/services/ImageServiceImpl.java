package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Image;
import com.telerikacademy.beertag.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
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
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> findById(Integer id) {
        return imageRepository.findById(id);
    }

    @Override
    public Optional<Image> save(MultipartFile file) {
        if (!isFileValid(file))
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

    private boolean isFileValid(MultipartFile file) {
        return Objects.equals(file.getContentType(), "image/jpeg") ||
                Objects.equals(file.getContentType(), "image/png");
    }
}
