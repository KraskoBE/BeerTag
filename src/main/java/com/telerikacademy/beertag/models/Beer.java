package com.telerikacademy.beertag.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telerikacademy.beertag.models.constants.BeerStyle;
import com.telerikacademy.beertag.models.constants.BeerType;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Where(clause = "enabled=1")
@Table(name = "beers")
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "beer_id")
    private int id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User creator;

    @NotNull
    @Column(name = "name")
    private String name;

    @Column(name = "brewery")
    private String brewery;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country originCountry;

    @Column(name = "abv")
    private double ABV;

    @Column(name = "description")
    private String description="No description. Why don't you add one?";

    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private BeerType type;

    @NotNull
    @Column(name = "style")
    @Enumerated(EnumType.STRING)
    private BeerStyle style;

    @NotNull
    @Column(name = "average_rating")
    private Double averageRating = 0.0;

    @NotNull
    @Column(name = "total_votes")
    private int totalVotes = 0;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToMany
    @JoinTable(
            name = "beer_tags",
            joinColumns = @JoinColumn(name = "beer_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> beerTags;

    @JsonIgnore
    @OneToMany(mappedBy = "beer")
    private Set<BeerRating> beerRatings;

    @NotNull
    @JsonIgnore
    @Column(name = "enabled")
    private boolean enabled = true;
}
