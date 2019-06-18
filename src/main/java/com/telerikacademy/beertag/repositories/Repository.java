package com.telerikacademy.beertag.repositories;


import java.util.List;

public interface Repository<T> {
    T add(T object);

    T get(int id);

    T update(T oldObject, T newObject);

    void remove(T object);

    List<T> getAll();

}
