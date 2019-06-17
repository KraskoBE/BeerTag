package com.telerikacademy.beertag.models;

import com.telerikacademy.beertag.models.constants.BeerStyle;
import com.telerikacademy.beertag.models.constants.BeerType;

import javax.persistence.*;


@Entity
@Table(name = "beers")
public class Beer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BeerId")
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "brewery")
    private String brewery;

    @Column(name = "originCountry")
    private String originCountry;

    @Column(name = "ABV")
    private double ABV;

    @Column(name = "Description")
    private String description;

    @Column(name = "Type")
    private BeerType type;

    @Column(name = "Style")
    private BeerStyle style;
    //private Image picture;


    public Beer() {
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
