package com.telerikacademy.beertag.models;

import com.telerikacademy.beertag.models.constants.BeerStyle;
import com.telerikacademy.beertag.models.constants.BeerType;

public class Beer {

    private int id;
    private String name;
    private String brewery;
    private String originCountry;
    private double ABV;
    private String description;
    private BeerType type;
    private BeerStyle style;
    //private Image picture;


    public Beer() {
    }

    public Beer(int id, String name, String brewery, String originCountry, double ABV, String description, BeerType type, BeerStyle style) {
        this.id = id;
        this.name = name;
        this.brewery = brewery;
        this.originCountry = originCountry;
        this.ABV = ABV;
        this.description = description;
        this.type = type;
        this.style = style;
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
