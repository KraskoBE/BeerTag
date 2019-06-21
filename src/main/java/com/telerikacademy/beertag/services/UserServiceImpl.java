package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.BeerRating;
import com.telerikacademy.beertag.models.BeerRatingId;
import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.stream.Collectors;


@org.springframework.stereotype.Service("UserService")
public class UserServiceImpl implements Service<User, Integer> {
    private Repository<User, Integer> userRepository;
    private Service<Beer, Integer> beerService;
    private Repository<BeerRating, BeerRatingId> beerRatingRepository;

    @Autowired
    public UserServiceImpl(Repository<User, Integer> userRepository,
                           Service<Beer, Integer> beerService,
                           Repository<BeerRating, BeerRatingId> beerRatingRepository) {
        this.userRepository = userRepository;
        this.beerService = beerService;
        this.beerRatingRepository = beerRatingRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User get(Integer id) {
        User user = userRepository.get(id);
        if (user == null)
            throw new IllegalArgumentException(String.format("User with id %d not found.", id));
        return user;
    }

    @Override
    public User add(User user) {
        checkDuplicateId(user);

        checkDuplicateEmail(user);

        return userRepository.add(user);
    }

    @Override
    public User update(Integer id, User user) {
        User oldUser = userRepository.get(id);
        if (!oldUser.getEmail().equals(user.getEmail())) {
            checkDuplicateEmail(user);
        }
        return userRepository.update(oldUser, user);
    }

    @Override
    public void remove(Integer id) {
        try {
            userRepository.remove(userRepository.get(id));
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void checkDuplicateEmail(User user) {
        List<User> duplicateEmails = userRepository.getAll()
                .stream()
                .filter(x -> x.getEmail().equals(user.getEmail()))
                .collect(Collectors.toList());

        if (duplicateEmails.size() > 0) {
            throw new IllegalArgumentException(String.format("User with the email %s already exists!", user.getEmail()));
        }
    }

    private void checkDuplicateId(User user) {
        List<User> duplicateIds = userRepository.getAll().stream()
                .filter(x -> x.getId() == user.getId())
                .collect(Collectors.toList());

        if (duplicateIds.size() > 0) {
            throw new IllegalArgumentException(String.format("User with the id of %d already exists", user.getId()));
        }

    }

    public Beer rateBeer(int userId, int beerId, int rating) {
        User user = get(userId);
        Beer beer = beerService.get(beerId);

        BeerRating beerRating = beerRatingRepository.get(new BeerRatingId(userId, beerId));
        if (rating == 0) {
            if (beerRating == null)
                return beer;
            else
                beerRatingRepository.remove(beerRating);
        } else {
            if (beerRating == null)
                beerRatingRepository.add(new BeerRating(user, beer, rating));
            else {
                beerRating.setRating(rating);
                beerRatingRepository.add(beerRating);
            }
        }
        return beer;
    }

    public Beer addToWishList(int userId, int beerId) {
        User user = get(userId);
        Beer beer = beerService.get(beerId);

        if (user.getWishList().contains(beer))
            throw new IllegalArgumentException("Beer already in wish list");

        user.getWishList().add(beer);
        userRepository.add(user);
        return beer;
    }

    public void removeFromWishList(int userId, int beerId) {
        User user = get(userId);
        Beer beer = beerService.get(beerId);

        if (!user.getWishList().contains(beer))
            throw new IllegalArgumentException("Beer not in wish list");

        user.getWishList().remove(beer);
        userRepository.add(user);
    }
}
