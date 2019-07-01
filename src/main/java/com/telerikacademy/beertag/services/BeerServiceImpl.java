package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.BeerRating;
import com.telerikacademy.beertag.models.BeerRatingId;
import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.repositories.BeerRatingRepository;
import com.telerikacademy.beertag.repositories.BeerRepository;
import com.telerikacademy.beertag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("BeerService")
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final UserRepository userRepository;
    private final BeerRatingRepository beerRatingRepository;

    @Autowired
    public BeerServiceImpl(BeerRepository beerRepository, UserRepository userRepository, BeerRatingRepository beerRatingRepository) {
        this.beerRepository = beerRepository;
        this.userRepository = userRepository;
        this.beerRatingRepository = beerRatingRepository;
    }

    @Override
    public List<Beer> findAll() {
        return beerRepository.findAll();
    }

    @Override
    public Optional<Beer> findById(Integer id) {
        return beerRepository.findById(id);
    }

    @Override
    public Optional<Beer> save(Integer creatorId, Beer beer) {
        Optional<Beer> oldBeer = beerRepository.findByName(beer.getName());
        if (oldBeer.isPresent() || !userRepository.findById(creatorId).isPresent())
            return Optional.empty();
        beer.setCreator(userRepository.findById(creatorId).get());
        return Optional.of(beerRepository.save(beer));
    }

    @Override
    public Optional<Beer> update(Integer id, Beer beer) {
        if (!beerRepository.findById(id).isPresent() || beerRepository.findByName(beer.getName()).isPresent())
            return Optional.empty();
        beer.setId(id);
        return Optional.of(beerRepository.save(beer));
    }

    @Override
    public void deleteById(Integer id) {
        beerRepository.deleteById(id);
    }

    @Override
    public Optional<Beer> rate(Integer userId, Integer beerId, Integer rating) {
        if (rating < 0 || rating > 5)
            return Optional.empty();

        if (!beerRepository.findById(beerId).isPresent() ||
                !userRepository.findById(userId).isPresent())
            return Optional.empty();
        User user = userRepository.findById(userId).get();
        Beer beer = beerRepository.findById(beerId).get();

        if (rating == 0) {
            beerRatingRepository.deleteById(new BeerRatingId(userId, beerId));
        } else {
            beerRatingRepository.save(new BeerRating(user, beer, rating));
        }


        System.out.println(beer.getBeerRatings().isEmpty());
        /*System.out.println("dai rekursiqta, gotin:" + beer.getBeerRatings().stream()
                .mapToDouble(BeerRating::getRating)
                .average()
                .orElse(0.0));*/

        /*beer.setAverageRating(beer.getBeerRatings().stream()
                .mapToDouble(BeerRating::getRating)
                .average()
                .orElse(0.0));*/

        return Optional.of(beer);
    }


}
