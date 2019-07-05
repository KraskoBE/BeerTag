package com.telerikacademy.beertag.service;


import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.repositories.UserRepository;
import com.telerikacademy.beertag.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTests {

    @Mock
    UserRepository mockUserRepo;
    @Mock
    PasswordEncoder mockPassEncoder;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void findById_Should_ReturnOptionalWithUser_When_MatchExists() {
        //Arrange
        final int USER_ID = 1;
        User user = new User();
        user.setId(USER_ID);
        Optional<User> optional = Optional.of(user);
        Mockito.when(mockUserRepo.findById(USER_ID)).thenReturn(optional);

        //Act
        Optional<User> result = userService.findById(1);

        //Assert
        Assert.assertTrue(result.isPresent());
    }
    @Test
    public void findById_Should_ReturnEmptyOptional_When_MatchDoesntExists() {
        //Arrange
        final int USER_ID = 1;
        Optional<User> optional = Optional.empty();
        Mockito.when(mockUserRepo.findById(USER_ID)).thenReturn(optional);

        //Act
        Optional<User> result = userService.findById(USER_ID);

        //Assert
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void save_Should_ReturnOptionalWithUser_When_Successful() {
        //Assert
        final String EMAIL = "example@email.com";
        User user = new User();
        user.setEmail(EMAIL);
        Optional<User> optional = Optional.empty();

        Mockito.when(mockUserRepo.findByEmail(EMAIL)).thenReturn(optional);
        Mockito.when(mockUserRepo.save(user)).thenReturn(user);

        //Act
        Optional<User> result = userService.save(user);

        //Assert
        Assert.assertTrue(result.isPresent());
    }
    @Test
    public void save_Should_ReturnEmptyOoptional_When_EmailAdressIsTaken() {
        //Assert
        final String EMAIL = "example@email.com";
        User user = new User();
        user.setEmail(EMAIL);
        Optional<User> optional = Optional.of(user);

        Mockito.when(mockUserRepo.findByEmail(EMAIL)).thenReturn(optional);
        Mockito.when(mockUserRepo.save(user)).thenReturn(user);

        //Act
        Optional<User> result = userService.save(user);

        //Assert
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void update_Should_ReturnOptionalWithUser_When_Successful() {
        //Arrange
        final int USER_ID = 1;
        final String EMAIL = "example@email.com";
        User newUser = new User();
        User oldUser = new User();
        oldUser.setId(USER_ID);
        newUser.setEmail(EMAIL);

        Mockito.when(userService.findById(USER_ID)).thenReturn(Optional.of(oldUser));
        Mockito.when(mockUserRepo.findByEmail(EMAIL)).thenReturn(Optional.empty());
        Mockito.when(mockUserRepo.save(newUser)).thenReturn(newUser);

        //Act
        Optional<User> result = userService.update(USER_ID, newUser);

        //Assert
        Assert.assertTrue(result.isPresent());
    }
    @Test
    public void update_Should_ReturnEmptyOptional_When_UserDoesntExist() {
        //Arrange
        final int USER_ID = 1;
        final String EMAIL = "example@email.com";
        User newUser = new User();
        User oldUser = new User();
        oldUser.setId(USER_ID);
        newUser.setEmail(EMAIL);

        Mockito.when(userService.findById(USER_ID)).thenReturn(Optional.empty());
        Mockito.when(mockUserRepo.findByEmail(EMAIL)).thenReturn(Optional.empty());
        Mockito.when(mockUserRepo.save(newUser)).thenReturn(newUser);

        //Act
        Optional<User> result = userService.update(USER_ID, newUser);

        //Assert
        Assert.assertFalse(result.isPresent());
    }
    @Test
    public void update_Should_ReturnEmptyOptional_When_UserEmailIsTaken() {
        //Arrange
        final int USER_ID = 1;
        final String EMAIL = "example@email.com";
        User newUser = new User();
        User oldUser = new User();
        oldUser.setId(USER_ID);
        oldUser.setEmail(EMAIL);
        newUser.setEmail(EMAIL);

        Mockito.when(userService.findById(USER_ID)).thenReturn(Optional.of(oldUser));
        Mockito.when(mockUserRepo.findByEmail(EMAIL)).thenReturn(Optional.of(oldUser));
        Mockito.when(mockUserRepo.save(newUser)).thenReturn(newUser);

        //Act
        Optional<User> result = userService.update(USER_ID, newUser);

        //Assert
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void delete_Should_SetEnabledToFalse_When_UserExists() {
        //TODO dont know how to test this
    }
    @Test
    public void delete_Should_ThrowNoSuchElementException_When_UserDoesntExist() {
        //TODO dont know how to test this
    }

    @Test
    public void register_Should_ReturnOptionalWithUser_When_Successful() {
        //Arrange
        final String EMAIL = "example@email.com";
        final String PASSWORD = "1234";
        User user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        Mockito.when(mockUserRepo.findByEmail(EMAIL)).thenReturn(Optional.empty());
        Mockito.when(mockUserRepo.save(user)).thenReturn(user);

        //Act
        Optional<User> result = userService.register(user);

        //Assert
        Assert.assertTrue(result.isPresent());
    }
    @Test
    public void register_Should_ReturnEmptyOptional_When_UserEmailIsTaken() {
        //Arrange
        final String EMAIL = "example@email.com";
        final String PASSWORD = "1234";
        User user = new User();
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        Mockito.when(mockUserRepo.findByEmail(EMAIL)).thenReturn(Optional.of(new User()));
        Mockito.when(mockUserRepo.save(user)).thenReturn(user);

        //Act
        Optional<User> result = userService.register(user);

        //Assert
        Assert.assertFalse(result.isPresent());
    }



}
