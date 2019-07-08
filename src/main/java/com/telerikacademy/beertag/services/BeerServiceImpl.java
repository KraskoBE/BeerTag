package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.*;
import com.telerikacademy.beertag.repositories.BeerRatingRepository;
import com.telerikacademy.beertag.repositories.BeerRepository;
import com.telerikacademy.beertag.repositories.TagRepository;
import com.telerikacademy.beertag.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service("BeerService")
public class BeerServiceImpl implements BeerService {
    private final BeerRepository beerRepository;
    private final UserRepository userRepository;
    private final BeerRatingRepository beerRatingRepository;
    private final TagRepository tagRepository;

    @Autowired
    public BeerServiceImpl(BeerRepository beerRepository,
                           UserRepository userRepository,
                           BeerRatingRepository beerRatingRepository,
                           TagRepository tagRepository) {
        this.beerRepository = beerRepository;
        this.userRepository = userRepository;
        this.beerRatingRepository = beerRatingRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Beer> findAll() {
        return beerRepository.findAll();
    }

    @Override
    public Page<Beer> findBy(Integer pageNum, String orderBy) {
        if (pageNum < 0)
            pageNum = 0;
        PageRequest pageRequest = PageRequest.of(pageNum, 4);
        switch (orderBy) {
            case "abv":
                return beerRepository.findAllByOrderByABVDesc(pageRequest);
            case "name":
                return beerRepository.findAllByOrderByNameAsc(pageRequest);
            default:
                return beerRepository.findAllByOrderByAverageRatingDesc(pageRequest);
        }
    }

    @Override
    public Optional<Beer> findById(Integer id) {
        return beerRepository.findById(id);
    }

    @Override
    public Optional<Beer> save(Integer creatorId, Beer beer, Image image) {
        Optional<Beer> oldBeer = beerRepository.findByName(beer.getName());
        if (oldBeer.isPresent() || !userRepository.findById(creatorId).isPresent())
            return Optional.empty();
        beer.setCreator(userRepository.findById(creatorId).get());
        beer.setImage(image);
        return Optional.of(beerRepository.save(beer));
    }

    @Override
    public Optional<Beer> update(Integer id, Beer beer) {
        if (!beerRepository.findById(id).isPresent() || beerRepository.findByName(beer.getName()).isPresent())
            return Optional.empty();

        Beer oldBeer = beerRepository.findById(id).get();
        oldBeer.setOriginCountry(beer.getOriginCountry());
        oldBeer.setBrewery(beer.getBrewery());
        oldBeer.setStyle(beer.getStyle());
        oldBeer.setType(beer.getType());
        oldBeer.setABV(beer.getABV());
        oldBeer.setDescription(beer.getDescription());

        beerRepository.save(oldBeer);

        return Optional.of(oldBeer);
    }

    @Override
    public void deleteById(Integer beerId, Integer userId) {
        Beer beer = getBeer(beerId);
        User user = getUser(userId);

        if(beer.getCreator() != user)
            return;

        beerRepository.deleteById(beerId);
    }


    @Override
    public Optional<Beer> rate(Integer userId, Integer beerId, Integer rating) {
        if (rating < 1 || rating > 5)
            return Optional.empty();

        Beer beer = getBeer(beerId);
        User user = getUser(userId);

        Optional<BeerRating> beerRating = beerRatingRepository.findById(new BeerRatingId(userId, beerId));
        beerRatingRepository.save(new BeerRating(user, beer, rating));

        beer.setAverageRating(beer.getBeerRatings().stream()
                .mapToDouble(BeerRating::getRating)
                .average()
                .orElse(0));

        beer.setTotalVotes(beer.getBeerRatings().size());
        beerRepository.save(beer);
        return Optional.of(beer);
    }

    @Override
    public Optional<Beer> tag(Integer userId, Integer beerId, String tagName) {
        Beer beer = getBeer(beerId);

        Optional<Tag> tagOptional = tagRepository.findByName(tagName.toLowerCase());
        Tag tag = tagOptional.orElseGet(() -> tagRepository.save(new Tag(tagName.toLowerCase())));

        if (beer.getBeerTags().contains(tag))
            return Optional.empty();

        beer.getBeerTags().add(tag);
        beerRepository.save(beer);
        return Optional.of(beer);
    }

    private User getUser(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent())
            throw new IllegalArgumentException("User not found"); //TODO make custom exception
        return userOptional.get();
    }

    private Beer getBeer(int beerId) {
        Optional<Beer> beerOptional = beerRepository.findById(beerId);
        if (!beerOptional.isPresent())
            throw new IllegalArgumentException("Beer not found"); //TODO make custom exception
        return beerOptional.get();
    }

}
