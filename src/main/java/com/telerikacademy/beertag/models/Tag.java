package com.telerikacademy.beertag.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Where(clause = "enabled=1")
@Table(name = "tags")
public class Tag {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private int id;

    @NotNull
    @Column(name = "name", unique = true, length = 10)
    private String name;

    @NotNull
    @Column(name = "enabled")
    private boolean enabled = true;
}
