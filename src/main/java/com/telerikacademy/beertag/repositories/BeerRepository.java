package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BeerRepository extends JpaRepository<Beer, Integer> {

    @Modifying
    @Query(value = "update beers b set b.enabled = 0 where b.beer_id = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);
}
