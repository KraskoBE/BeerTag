package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Integer> {
    @Modifying
    @Query(value = "update tags t set t.enabled = 0 where t.tag_id = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);
}
