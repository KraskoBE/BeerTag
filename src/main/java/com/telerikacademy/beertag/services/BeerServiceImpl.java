package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.BeerRating;
import com.telerikacademy.beertag.models.BeerRatingId;
import com.telerikacademy.beertag.models.User;
import com.telerikacademy.beertag.repositories.BeerRatingRepository;
import com.telerikacademy.beertag.repositories.BeerRepository;
import com.telerikacademy.beertag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        //throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found");
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

        Optional<Beer> beerOptional = beerRepository.findById(beerId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (!beerOptional.isPresent() ||
                !userOptional.isPresent())
            return Optional.empty();

        Beer beer = beerOptional.get();
        User user = userOptional.get();

        Optional<BeerRating> beerRating = beerRatingRepository.findById(new BeerRatingId(userId, beerId));
        if (rating == 0) {
            beerRating.ifPresent(beerRatingRepository::delete);
        } else {
            beerRatingRepository.save(new BeerRating(user, beer, rating));
        }

        beer.setAverageRating(beer.getBeerRatings().stream()
                .mapToDouble(BeerRating::getRating)
                .average()
                .orElse(0));

        beer.setTotalVotes(beer.getBeerRatings().size());

        return beerRepository.findById(beerId);
    }

}
