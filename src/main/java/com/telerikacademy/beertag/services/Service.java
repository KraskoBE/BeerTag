package com.telerikacademy.beertag.services;


import java.util.List;

public interface Service<T> {
    T add(T object);

    T get(int id);

    T update(int id, T newObject);

    T remove(int id);

    List<T> getAll();
}
