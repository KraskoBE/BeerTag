package com.telerikacademy.beertag.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@IdClass(BeerRatingId.class)
public class BeerRating {
    @Id
    @ManyToOne
    private User user;

    @Id
    @ManyToOne
    private Beer beer;

    private int rating;

    public BeerRating() {
    }

    public BeerRating(User user, Beer beer, int rating) {
        this.user = user;
        this.beer = beer;
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
