package com.telerikacademy.beertag.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BeerRatingId implements Serializable {
    private int user;
    private int beer;

}
