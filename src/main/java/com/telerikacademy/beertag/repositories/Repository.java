package com.telerikacademy.beertag.repositories;


import java.util.List;

public interface Repository<T> {
    T add(T beer);

    T get(int id);

    T update(T oldBeer, T newBeer);

    void remove(T beer);

    List<T> getAll();

}
