package com.telerikacademy.beertag.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
