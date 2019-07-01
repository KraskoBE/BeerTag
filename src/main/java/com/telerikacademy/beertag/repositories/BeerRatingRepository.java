package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.BeerRating;
import com.telerikacademy.beertag.models.BeerRatingId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRatingRepository extends JpaRepository<BeerRating, BeerRatingId> {
}
