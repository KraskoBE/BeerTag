package com.telerikacademy.beertag.services;


import java.util.List;

public interface Service<T> {
    T add(T beer);

    T get(int id);

    T update(int id, T newObject);

    void remove(int id);

    List<T> getAll();
}
