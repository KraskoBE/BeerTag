package com.telerikacademy.beertag.models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String email;
    private String password;

    private String name;
    private int age;
    private List<Beer> wishList;
    private List<Beer> drankList;

    public User(){}

    public User(String email, String password, String name, int age){
        this.email = email;
        this.password = password;

        this.name = name;
        this.age = age;
        wishList = new ArrayList<>();
        drankList = new ArrayList<>();
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
