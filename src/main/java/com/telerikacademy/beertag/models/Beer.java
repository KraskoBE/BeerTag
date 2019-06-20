package com.telerikacademy.beertag.models;

import com.telerikacademy.beertag.models.constants.BeerStyle;
import com.telerikacademy.beertag.models.constants.BeerType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Entity
@Table(name = "Beers")
public class Beer {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BeerId")
    private int id;

    @NotNull
    @Column(name = "Name")
    private String name;

    @Column(name = "Brewery")
    private String brewery;

    @ManyToOne
    @JoinColumn(name = "CountryId")
    private Country originCountry;

    @Column(name = "ABV")
    private double ABV;

    @Column(name = "Description")
    private String description;

    @Column(name = "Type")
    @Enumerated(EnumType.STRING)
    private BeerType type;

    @Column(name = "Style")
    @Enumerated(EnumType.STRING)
    private BeerStyle style;

    @ManyToMany
    @JoinTable(
            name = "BeerTags",
            joinColumns = @JoinColumn(name = "BeerId"),
            inverseJoinColumns = @JoinColumn(name = "TagId")
    )
    private Set<Tag> beerTags;

    @OneToMany(mappedBy = "beer")
    private Set<UserRatingBeer> userRatingBeers;


    public Beer() {
    }

    public Set<Tag> getBeerTags() {
        return beerTags;
    }

    public void setBeerTags(Set<Tag> beerTags) {
        this.beerTags = beerTags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public Country getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(Country originCountry) {
        this.originCountry = originCountry;
    }

    public double getABV() {
        return ABV;
    }

    public void setABV(double ABV) {
        this.ABV = ABV;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BeerType getType() {
        return type;
    }

    public void setType(BeerType type) {
        this.type = type;
    }

    public BeerStyle getStyle() {
        return style;
    }

    public void setStyle(BeerStyle style) {
        this.style = style;
    }
}
