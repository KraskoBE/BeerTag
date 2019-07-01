package com.telerikacademy.beertag.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BeerRatingId implements Serializable {
    private int user;
    private int beer;

}
