package com.telerikacademy.beertag.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@IdClass(BeerRatingId.class)
@Table(name="beer_rating")
public class BeerRating {
    @Id
    @ManyToOne
    private User user;

    @Id
    @ManyToOne
    private Beer beer;

    private int rating;

}
