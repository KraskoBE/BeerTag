package com.telerikacademy.beertag.service;

import com.telerikacademy.beertag.models.*;
import com.telerikacademy.beertag.repositories.BeerRatingRepository;
import com.telerikacademy.beertag.repositories.BeerRepository;
import com.telerikacademy.beertag.repositories.UserRepository;
import com.telerikacademy.beertag.services.BeerServiceImpl;
import com.telerikacademy.beertag.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BeerServiceTests {

    @Mock
    BeerRepository mockBeerRepo;
    @Mock
    UserRepository mockUserRepo;
    @Mock
    BeerRatingRepository beerRatingRepo;

    @InjectMocks
    BeerServiceImpl beerService;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void findById_Should_ReturnOptionalWithBeer_When_MatchExists() {
        //Arrange
        Beer beer = new Beer();
        beer.setId(1);
        Optional<Beer> optional = Optional.of(beer);
        Mockito.when(mockBeerRepo.findById(Mockito.anyInt())).thenReturn(optional);

        //Act
        Optional<Beer> result = beerService.findById(1);

        //Assert
        Assert.assertTrue(result.isPresent());
    }
    @Test
    public void findById_Should_ReturnEmptyOptional_When_MatchDoesntExists() {
        //Arrange
        Optional<Beer> optional = Optional.empty();
        Mockito.when(mockBeerRepo.findById(Mockito.anyInt())).thenReturn(optional);

        //Act
        Optional<Beer> result = beerService.findById(1);

        //Assert
        Assert.assertFalse(result.isPresent());
    }
/*
    @Test
    public void save_Should_ReturnOptionalWithBeer_When_SaveIsSuccessful() {
        //Arrange
        Beer newBeer = new Beer();
        final String NEW = "new";
        Image image = new Image();
        newBeer.setName(NEW);
        Optional<Beer> oldBeerOptional = Optional.empty();
        Mockito.when(mockBeerRepo.findByName(NEW)).thenReturn(oldBeerOptional);

        User creator = new User();
        final int CREATOR_ID = 1;
        creator.setId(CREATOR_ID);
        Optional<User> creatorOptional = Optional.of(creator);

        Mockito.when(mockUserRepo.findById(CREATOR_ID)).thenReturn(creatorOptional);

        Mockito.when(mockBeerRepo.save(newBeer)).thenReturn(newBeer);

        //Act
        Optional<Beer> result = beerService.save(CREATOR_ID, newBeer, image);

        //Assert
        Assert.assertTrue(result.isPresent());
    }
    @Test
    public void save_Should_ReturnEmptyOptional_When_BeerAlreadyExists() {
        //Arrange
        Beer newBeer = new Beer();
        final String NEW = "new";
        Image image = new Image();
        newBeer.setName(NEW);
        Optional<Beer> oldBeerOptional = Optional.of(newBeer);
        Mockito.when(mockBeerRepo.findByName(NEW)).thenReturn(oldBeerOptional);

        User creator = new User();
        final int CREATOR_ID = 1;
        creator.setId(CREATOR_ID);
        Optional<User> creatorOptional = Optional.of(creator);

        Mockito.when(mockUserRepo.findById(CREATOR_ID)).thenReturn(creatorOptional);

        Mockito.when(mockBeerRepo.save(newBeer)).thenReturn(newBeer);

        //Act
        Optional<Beer> result = beerService.save(CREATOR_ID, newBeer, image);

        //Assert
        Assert.assertFalse(result.isPresent());
    }
    @Test
    public void save_Should_ReturnEmptyOptional_When_CreatorDoesntExist() {
        //Arrange
        Beer newBeer = new Beer();
        final String NEW = "new";
        Image image = new Image();
        newBeer.setName(NEW);
        Optional<Beer> oldBeerOptional = Optional.of(newBeer);
        Mockito.when(mockBeerRepo.findByName(NEW)).thenReturn(oldBeerOptional);

        User creator = new User();
        final int CREATOR_ID = 1;
        creator.setId(CREATOR_ID);
        Optional<User> creatorOptional = Optional.empty();

        Mockito.when(mockUserRepo.findById(CREATOR_ID)).thenReturn(creatorOptional);

        Mockito.when(mockBeerRepo.save(newBeer)).thenReturn(newBeer);

        //Act
        Optional<Beer> result = beerService.save(CREATOR_ID, newBeer, image);

        //Assert
        Assert.assertFalse(result.isPresent());
    }
*/
    @Test
    public void update_Should_ReturnOptionalWithBeer_When_Seuccessful() {
        //Arrange
        final int BEER_ID = 1;
        final String NEW = "new";
        Beer updatedBeer = new Beer();
        updatedBeer.setName(NEW);
        Optional<Beer> oldBeerOptional = Optional.of(new Beer());

        Mockito.when(mockBeerRepo.findById(BEER_ID)).thenReturn(oldBeerOptional);
        Mockito.when(mockBeerRepo.findByName(NEW)).thenReturn(Optional.empty());
        Mockito.when(mockBeerRepo.save(updatedBeer)).thenReturn(updatedBeer);

        //Act
        Optional<Beer> result = beerService.update(BEER_ID, updatedBeer);

        //Assert
        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void update_Should_ReturnEmptyOptional_When_BeerDoesntExist() {
        //Arrange
        final int BEER_ID = 1;
        final String NEW = "new";
        Beer updatedBeer = new Beer();
        updatedBeer.setName(NEW);
        Optional<Beer> oldBeerOptional = Optional.empty();

        Mockito.when(mockBeerRepo.findById(BEER_ID)).thenReturn(oldBeerOptional);
        Mockito.when(mockBeerRepo.findByName(NEW)).thenReturn(Optional.empty());
        Mockito.when(mockBeerRepo.save(updatedBeer)).thenReturn(updatedBeer);

        //Act
        Optional<Beer> result = beerService.update(BEER_ID, updatedBeer);

        //Assert
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void update_Should_ReturnEmptyOptional_When_BeerWithTheNewNameAlreadyExists() {
        //Arrange
        final int BEER_ID = 1;
        final String NEW = "new";
        Beer updatedBeer = new Beer();
        updatedBeer.setName(NEW);
        Optional<Beer> oldBeerOptional = Optional.of(new Beer());

        Mockito.when(mockBeerRepo.findById(BEER_ID)).thenReturn(oldBeerOptional);
        Mockito.when(mockBeerRepo.findByName(NEW)).thenReturn(Optional.of(updatedBeer));
        Mockito.when(mockBeerRepo.save(updatedBeer)).thenReturn(updatedBeer);

        //Act
        Optional<Beer> result = beerService.update(BEER_ID, updatedBeer);

        //Assert
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void delete_Should_SetEnabledToFalse_When_BeerExists() {
        //TODO dont know how to test this
    }
    @Test
    public void delete_Should_ThrowNoSuchElementException_When_BeerDoesntExist() {
        //TODO dont know how to test this
    }

    @Test
    public void rate_Should_ReturnOptionalWithBeer_When_Successful() {
        //Arrange
        final int USER_ID = 1;
        final int BEER_ID = 1;
        final int RATING = 1;

        Beer beer = new Beer();
        beer.setId(BEER_ID);
        beer.setBeerRatings(new HashSet<BeerRating>());

        User user = new User();
        user.setId(USER_ID);

        BeerRatingId beerRatingId = new BeerRatingId(USER_ID,BEER_ID);
        BeerRating beerRating = new BeerRating();
        beerRating.setRating(RATING);

        Mockito.when(mockBeerRepo.findById(BEER_ID)).thenReturn(Optional.of(beer));
        Mockito.when(mockUserRepo.findById(USER_ID)).thenReturn(Optional.of(user));
        Mockito.when(beerRatingRepo.findById(beerRatingId)).thenReturn(Optional.of(beerRating));

        //Act
        Optional<Beer> result = beerService.rate(USER_ID,BEER_ID,RATING);

        //Arrange
        Assert.assertTrue(result.isPresent());
    }

    @Test
    public void rate_Should_ReturnEmptyOptional_When_RatingIsInvalid() {
        //Arrange
        final int USER_ID = 1;
        final int BEER_ID = 1;
        final int RATING = 10;

        Beer beer = new Beer();
        beer.setId(BEER_ID);
        beer.setBeerRatings(new HashSet<BeerRating>());

        User user = new User();
        user.setId(USER_ID);

        BeerRatingId beerRatingId = new BeerRatingId(USER_ID,BEER_ID);
        BeerRating beerRating = new BeerRating();
        beerRating.setRating(RATING);

        Mockito.when(mockBeerRepo.findById(BEER_ID)).thenReturn(Optional.of(beer));
        Mockito.when(mockUserRepo.findById(USER_ID)).thenReturn(Optional.of(user));
        Mockito.when(beerRatingRepo.findById(beerRatingId)).thenReturn(Optional.of(beerRating));

        //Act
        Optional<Beer> result = beerService.rate(USER_ID,BEER_ID,RATING);

        //Arrange
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void rate_Should_ReturnEmptyOptional_When_BeerOrUserDoesntExist() {
        //Arrange
        final int USER_ID = 1;
        final int BEER_ID = 1;
        final int RATING = 1;

        Beer beer = new Beer();
        beer.setId(BEER_ID);
        beer.setBeerRatings(new HashSet<BeerRating>());

        User user = new User();
        user.setId(USER_ID);

        BeerRatingId beerRatingId = new BeerRatingId(USER_ID,BEER_ID);
        BeerRating beerRating = new BeerRating();
        beerRating.setRating(RATING);

        Mockito.when(mockBeerRepo.findById(BEER_ID)).thenReturn(Optional.empty());
        Mockito.when(mockUserRepo.findById(USER_ID)).thenReturn(Optional.empty());
        Mockito.when(beerRatingRepo.findById(beerRatingId)).thenReturn(Optional.of(beerRating));

        //Act
        Optional<Beer> result = beerService.rate(USER_ID,BEER_ID,RATING);

        //Arrange
        Assert.assertFalse(result.isPresent());
    }

}
