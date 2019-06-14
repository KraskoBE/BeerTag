package com.telerikacademy.beertag.models;

public class Beer {

    private String name;
    private String brewery;
    private String originCountry;
    private double ABV;
    private String description;
    //private BeerStyle style;
    //private Image picture;


    public Beer() {
    }

    public Beer(String name, String brewery, String originCountry, double ABV, String description) {
        this.name = name;
        this.brewery = brewery;
        this.originCountry = originCountry;
        this.ABV = ABV;
        this.description = description;
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
}
