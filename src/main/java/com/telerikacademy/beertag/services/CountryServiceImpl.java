package com.telerikacademy.beertag.services;

import com.telerikacademy.beertag.models.Country;
import com.telerikacademy.beertag.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service("CountryService")
public class CountryServiceImpl implements Service<Country, Integer> {
    private Repository<Country, Integer> countryRepository;

    @Autowired
    public CountryServiceImpl(Repository<Country, Integer> countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country add(Country country) {
        List<Country> countries = getAll().stream()
                .filter(country1 -> country1.getName().equals(country.getName()) ||
                        country1.getCode().equals(country.getCode()))
                .collect(Collectors.toList());

        if (countries.size() > 0) {
            throw new IllegalArgumentException("Country already exists");
        }

        return countryRepository.add(country);
    }

    @Override
    public Country get(Integer id) {
        Country country = countryRepository.get(id);
        if (country == null)
            throw new IllegalArgumentException(String.format("Country with id %d not found.", id));
        return country;
    }

    @Override
    public Country update(Integer id, Country newCountry) {
        Country oldCountry = get(id);
        return countryRepository.update(oldCountry, newCountry);
    }

    @Override
    public void remove(Integer id) {
        countryRepository.remove(get(id));
    }

    @Override
    public List<Country> getAll() {
        return countryRepository.getAll();
    }
}
