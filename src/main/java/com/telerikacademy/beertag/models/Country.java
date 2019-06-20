package com.telerikacademy.beertag.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Countries")
public class Country {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CountryId")
    private int id;

    @NotNull
    @Column(name = "CountryCode", unique = true, length = 100)
    private String code;

    @NotNull
    @Column(name = "CountryName", unique = true, length = 100)
    private String name;

    public Country() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
