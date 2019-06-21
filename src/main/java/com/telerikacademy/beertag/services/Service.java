package com.telerikacademy.beertag.services;


import java.io.Serializable;
import java.util.List;

public interface Service<T, ID extends Serializable> {
    T add(T object);

    T get(ID id);

    T update(ID id, T newObject);

    void remove(ID id);

    List<T> getAll();
}
