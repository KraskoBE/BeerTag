package com.telerikacademy.beertag.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private int id;

    @NotNull
    @Column(name = "Name")
    private String name;

    @Column(name = "Age")
    private int age;

    @ManyToMany
    @JoinTable(
            name = "WishList",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "BeerId")
    )
    @JsonIgnore
    private Set<Beer> wishList;

    @ManyToMany
    @JoinTable(
            name = "DrankList",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "BeerId")
    )
    @JsonIgnore
    private Set<Beer> drankList;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<BeerRating> beerRatings;

    @Column(name = "WishListIds")
    public Set<Integer> getWishListIds() {
        return wishList.stream()
                .map(Beer::getId)
                .collect(Collectors.toSet());
    }

    @Column(name = "DrankListIds")
    public Set<Integer> getDrankListIds() {
        return drankList.stream()
                .map(Beer::getId)
                .collect(Collectors.toSet());
    }

    public User() {
        wishList = new HashSet<>();
        drankList = new HashSet<>();
    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        wishList = new HashSet<>();
        drankList = new HashSet<>();
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Set<Beer> getWishList() {
        return wishList;
    }

    public Set<Beer> getDrankList() {
        return drankList;
    }
}
