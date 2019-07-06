package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Beer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BeerRepository extends JpaRepository<Beer, Integer> {

    @Modifying
    @Query(value = "update beers b set b.enabled = 0 where b.beer_id = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);

    Optional<Beer> findByName(String name);

    Page<Beer> findAllByOrderByAverageRatingDesc(Pageable pageable);
    Page<Beer> findAllByOrderByABVDesc(Pageable pageable);
    Page<Beer> findAllByOrderByNameAsc(Pageable pageable);
}
