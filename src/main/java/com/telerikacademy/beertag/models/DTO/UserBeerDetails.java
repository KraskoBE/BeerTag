package com.telerikacademy.beertag.models.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserBeerDetails {
    private int userId;
    private int beerId;
    private int rating;
    private boolean wished;
    private boolean drunk;
}
