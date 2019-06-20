package com.telerikacademy.beertag.models;

import javax.persistence.*;

@Entity
@IdClass(UserRatingBeerId.class)
public class UserRatingBeer {
    @Id
    @ManyToOne
    private User user;

    @Id
    @ManyToOne
    private Beer beer;

    private int rating;

    public UserRatingBeer() {
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
