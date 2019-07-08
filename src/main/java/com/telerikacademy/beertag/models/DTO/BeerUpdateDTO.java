package com.telerikacademy.beertag.models.DTO;

import com.telerikacademy.beertag.models.Country;
import com.telerikacademy.beertag.models.constants.BeerStyle;
import com.telerikacademy.beertag.models.constants.BeerType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BeerUpdateDTO {

    private String name;

    private String brewery;

    private Country originCountry;

    private double ABV;

    private BeerType type;

    private BeerStyle style;

}
