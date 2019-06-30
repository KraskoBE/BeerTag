package com.telerikacademy.beertag.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class BeerRatingId implements Serializable {
    private int user;
    private int beer;

}
