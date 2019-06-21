package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.BeerRating;
import com.telerikacademy.beertag.models.BeerRatingId;
import com.telerikacademy.beertag.models.Country;
import com.telerikacademy.beertag.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service("BeerService")
public class BeerServiceImpl implements Service<Beer, Integer> {

    private Repository<Beer, Integer> beerRepository;
    private Repository<Country, Integer> countryRepository;
    private Repository<BeerRating, BeerRatingId> beerRatingRepository;

    @Autowired
    public BeerServiceImpl(Repository<Beer, Integer> beerRepository,
                           Repository<Country, Integer> countryRepository,
                           Repository<BeerRating, BeerRatingId> beerRatingRepository) {
        this.beerRepository = beerRepository;
        this.countryRepository = countryRepository;
        this.beerRatingRepository = beerRatingRepository;
    }

    @Override
    public Beer add(Beer beer) {
        List<Beer> beers = getAll().stream()
                .filter(beer1 -> beer1.getName().equals(beer.getName()))
                .collect(Collectors.toList());

        if (beers.size() > 0) {
            throw new IllegalArgumentException("Beer already exists");
        }

        beer.setOriginCountry(countryRepository.get(beer.getOriginCountry().getId()));
        return beerRepository.add(beer);
    }

    @Override
    public Beer get(Integer id) {
        Beer beer = beerRepository.get(id);
        if (beer == null)
            throw new IllegalArgumentException(String.format("Beer with id %d not found.", id));
        return beer;
    }

    @Override
    public Beer update(Integer id, Beer newBeer) {
        Beer oldBeer = get(id);
        return beerRepository.update(oldBeer, newBeer);
    }

    @Override
    public void remove(Integer id) {
        beerRepository.remove(get(id));
    }

    @Override
    public List<Beer> getAll() {
        return beerRepository.getAll();
    }
}
