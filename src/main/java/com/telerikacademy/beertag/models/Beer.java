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

    @NotNull
    @Column(name = "Brewery")
    private String brewery;

    @NotNull
    @Column(name = "OriginCountry")
    private String originCountry;

    @NotNull
    @Column(name = "ABV")
    private double ABV;

    @NotNull
    @Column(name = "Description")
    private String description;

    @NotNull
    @Column(name = "Type")
    @Enumerated(EnumType.STRING)
    private BeerType type;

    @NotNull
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

    public Set<Tag> getBeerTags() {
        return beerTags;
    }

    public void setBeerTags(Set<Tag> beerTags) {
        this.beerTags = beerTags;
    }

    //private Image picture;


    public Beer() {
        // beerTags =  new ArrayList<>();
    }

    /*public Beer(int id, String name, String brewery, String originCountry, double ABV, String description, BeerType type, BeerStyle style) {
        this.id = id;
        this.name = name;
        this.brewery = brewery;
        this.originCountry = originCountry;
        this.ABV = ABV;
        this.description = description;
        this.type = type;
        this.style = style;
    }*/

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

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
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
