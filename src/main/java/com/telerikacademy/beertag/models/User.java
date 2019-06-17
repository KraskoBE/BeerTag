package com.telerikacademy.beertag.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private int id;

    @Column(name = "Email")
    private String email;

    @Column(name = "Password")
    private String password;

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
    private List<Beer> wishList;

    @ManyToMany
    @JoinTable(
            name = "DrankList",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "BeerId")
    )
    @JsonIgnore
    private List<Beer> drankList;

    public User() {
    }

    public User(int id, String email, String password, String name, int age) {
        this.id = id;
        this.email = email;
        this.password = password;

        this.name = name;
        this.age = age;
        wishList = new ArrayList<>();
        drankList = new ArrayList<>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Beer> getWishList() {
        return wishList;
    }

    public List<Beer> getDrankList() {
        return drankList;
    }
}
