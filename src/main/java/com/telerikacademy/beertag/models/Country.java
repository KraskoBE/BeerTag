package com.telerikacademy.beertag.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Where(clause = "enabled=1")
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
    @JsonIgnore
    private boolean enabled = true;
}
