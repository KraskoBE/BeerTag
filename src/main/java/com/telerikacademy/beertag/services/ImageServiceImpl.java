package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Image;
import com.telerikacademy.beertag.repositories.ImageRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@org.springframework.stereotype.Service("ImageService")
public class ImageServiceImpl implements Service<Image, Integer> {
    private ImageRepositoryImpl imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepositoryImpl imageRepository) {
        this.imageRepository = imageRepository;
    }


    @Override
    public Image add(Image image) {
        return imageRepository.add(image);
    }

    @Override
    public Image get(Integer id) {
        Image image = imageRepository.get(id);
        if (image == null)
            throw new IllegalArgumentException(String.format("Image with id %d not found.", id));
        return image;
    }

    @Override
    public Image update(Integer id, Image newImage) {
        Image oldImage = get(id);
        return imageRepository.update(oldImage, newImage);
    }

    @Override
    public void remove(Integer id) {
        try {
            imageRepository.remove(imageRepository.get(id));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public List<Image> getAll() {
        return imageRepository.getAll();
    }
}
