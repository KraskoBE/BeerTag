package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Query(value = "update users u set u.enabled = 0 where u.user_id = :id", nativeQuery = true)
    void deleteById(@Param("id") Integer id);

    Optional<User> findByEmail(String email);
}
