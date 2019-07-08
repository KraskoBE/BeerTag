package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.*;
import com.telerikacademy.beertag.models.DTO.UserBeerDetails;
import com.telerikacademy.beertag.models.DTO.UserUpdateDTO;
import com.telerikacademy.beertag.repositories.BeerRatingRepository;
import com.telerikacademy.beertag.repositories.BeerRepository;
import com.telerikacademy.beertag.repositories.ImageRepository;
import com.telerikacademy.beertag.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Base64;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("UserService")
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final BeerRepository beerRepository;
    private final BeerRatingRepository beerRatingRepository;
    private final ImageRepository imageRepository;

    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           BeerRepository beerRepository,
                           BeerRatingRepository beerRatingRepository,
                           ImageRepository imageRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.beerRepository = beerRepository;
        this.beerRatingRepository = beerRatingRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> save(User user) {
        Optional<User> oldUser = userRepository.findByEmail(user.getEmail());
        if (oldUser.isPresent())
            return Optional.empty();
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> update(Integer id, UserUpdateDTO userUpdateDTO) {
        User user = getUser(id);
        user.setAge(userUpdateDTO.getAge());

        if (userUpdateDTO.getImage() != null && !userUpdateDTO.getImage().equals("")) {
            byte[] imageBytes = Base64.getDecoder().decode(userUpdateDTO.getImage());
            Image image = imageRepository.save(new Image(imageBytes));
            user.setImage(image);
        }

        userRepository.save(user);
        return Optional.of(user);
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> register(User user) {
        Optional<User> oldUser = userRepository.findByEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (oldUser.isPresent())
            return Optional.empty();
        return Optional.of(userRepository.save(user));
    }

    @Override
    public Optional<User> wish(Integer userId, Integer beerId) {
        Beer beer = getBeer(beerId);
        User user = getUser(userId);

        if (user.getWishList().contains(beer))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Beer already in wish list"); //TODO make custom exception

        user.getWishList().add(beer);
        userRepository.save(user);
        return Optional.of(user);
    }

    @Override
    public Optional<User> drink(Integer userId, Integer beerId) {
        Beer beer = getBeer(beerId);
        User user = getUser(userId);

        if (user.getDrankList().contains(beer))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Beer already drunk"); //TODO make custom exception

        user.getWishList().remove(beer);
        user.getDrankList().add(beer);

        userRepository.save(user);
        return Optional.of(user);
    }

    @Override
    public Optional<UserBeerDetails> beerDetails(Integer userId, Integer beerId) {
        Beer beer = getBeer(beerId);
        User user = getUser(userId);

        UserBeerDetails beerDetails = new UserBeerDetails();
        beerDetails.setUserId(userId);
        beerDetails.setBeerId(beerId);
        beerDetails.setRating(beerRatingRepository
                .findById(new BeerRatingId(userId, beerId))
                .map(BeerRating::getRating)
                .orElse(0));
        beerDetails.setWished(user.getWishList().contains(beer));
        beerDetails.setDrunk(user.getDrankList().contains(beer));

        return Optional.of(beerDetails);
    }

    @Override
    public List<Beer> findWishedBeers(Integer userId) {
        User user = getUser(userId);
        return user.getWishList()
                .stream()
                .sorted(Comparator.comparing(Beer::getAverageRating).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Beer> findCreatedBeers(Integer userId) {
        User user = getUser(userId);
        return user.getCreatedBeers()
                .stream()
                .sorted(Comparator.comparing(Beer::getAverageRating).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Beer> findTopDrank(Integer userId) {
        User user = getUser(userId);
        return user.getDrankList()
                .stream()
                .sorted(Comparator.comparing(Beer::getAverageRating).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }


    private User getUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User not found"); //TODO make custom exception
        return userOptional.get();
    }

    private Beer getBeer(int beerId) {
        Optional<Beer> beerOptional = beerRepository.findById(beerId);
        if (!beerOptional.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Beer not found"); //TODO make custom exception
        return beerOptional.get();
    }


}
