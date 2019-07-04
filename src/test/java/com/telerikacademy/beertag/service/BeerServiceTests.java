package com.telerikacademy.beertag;

import com.telerikacademy.beertag.repositories.BeerRepository;
import com.telerikacademy.beertag.services.BeerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BeerServiceImplTests {

    @Mock
    BeerRepository mockRepository;

    @InjectMocks
    BeerServiceImpl service;

    @Test
    public void findById_Should_ReturnOptionalWithBeer_When_MatchExists() {

    }
    @Test
    public void findById_Should_ReturnEmptyOptional_When_MatchDoesntExists() {

    }

    @Test
    public void save_Should_ReturnOptionalWithBeer_When_SaveIsSuccessfull() {

    }
    @Test
    public void save_Should_ReturnEmptyOptional_When_BeerAlreadyExists() {

    }
    @Test
    public void save_Should_ReturnEmptyOptional_When_CreaterDoesntExist() {

    }

    @Test
    public void update_Should_ReturnOptionalWithBeer_When_Successfull() {

    }
    @Test
    public void update_Should_ReturnEmptyOptional_When_BeerDoesntExist() {

    }
    @Test
    public void update_Should_REturnEmptyOptional_When_BeerWithTheNewNameAlreadyExists() {

    }
}
