package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Image;
import com.telerikacademy.beertag.repositories.ImageRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

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
            throw new IllegalArgumentException("Image not found");
        return image;
    }

    @Override
    public Image update(Integer id, Image newObject) {
        return imageRepository.update(get(id), newObject);
    }

    @Override
    public void remove(Integer id) {
        imageRepository.remove(get(id));
    }

    @Override
    public List<Image> getAll() {
        return imageRepository.getAll();
    }

    /*public Image updateByBytes(Integer id, byte[] bytes)
    {
        return imageRepository.updateByBytes(get(id), bytes);
    }*/
}
