package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Modifying
    @Query(value = "update countries c set c.enabled = 0 where c.country_id = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);
}
