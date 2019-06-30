package com.telerikacademy.beertag.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private int id;

    @NotNull
    @Column(name = "code", unique = true, length = 100)
    private String code;

    @NotNull
    @Column(name = "name", unique = true, length = 100)
    private String name;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled = true;
}
