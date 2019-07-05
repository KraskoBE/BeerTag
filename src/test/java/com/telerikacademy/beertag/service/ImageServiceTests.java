package com.telerikacademy.beertag.service;


import com.telerikacademy.beertag.models.Image;
import com.telerikacademy.beertag.repositories.ImageRepository;
import com.telerikacademy.beertag.services.ImageServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ImageServiceTests {

    @Mock
    ImageRepository mockImageRepo;

    @InjectMocks
    ImageServiceImpl imageService;

    @Test
    public void findById_Should_ReturnOptionalWithImage_When_MatchExists() {
        //Arrange
        final int IMAGE_ID = 1;
        Image image = new Image();
        image.setId(IMAGE_ID);
        Optional<Image> optional = Optional.of(image);
        Mockito.when(mockImageRepo.findById(IMAGE_ID)).thenReturn(optional);

        //Act
        Optional<Image> result = imageService.findById(IMAGE_ID);

        //Assert
        Assert.assertTrue(result.isPresent());
    }
    @Test
    public void findById_Should_ReturnEmptyOptional_When_MatchDoesntExists() {
        //Arrange
        final int IMAGE_ID = 1;
        Image image = new Image();
        image.setId(IMAGE_ID);
        Optional<Image> optional = Optional.empty();
        Mockito.when(mockImageRepo.findById(IMAGE_ID)).thenReturn(optional);

        //Act
        Optional<Image> result = imageService.findById(IMAGE_ID);

        //Assert
        Assert.assertFalse(result.isPresent());
    }

    //TODO find out how to mock multipart files

    @Test
    public void save_Should_ReturnOptionalWithImage_When_Successful() {

    }
    @Test
    public void save_Should_ReturnEmptyOptional_When_FileIsInvalid() {

    }

    @Test
    public void update_Should_ReturnOptionalWithImage_When_Successful() {

    }
    @Test
    public void update_Should_ReturnEmptyOptional_When_FileIsInvalid() {

    }
    @Test
    public void update_Should_ReturnEmptyOptional_When_ImageAlreadyExists() {

    }

    @Test
    public void delete_Should_SetEnabledToFalse_When_ImageExists() {
        //TODO dont know how to test this
    }
    @Test
    public void delete_Should_ThrowNoSuchElementException_When_ImageDoesntExist() {
        //TODO dont know how to test this
    }
}
