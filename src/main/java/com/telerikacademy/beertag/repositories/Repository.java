package com.telerikacademy.beertag.repositories;


import java.io.Serializable;
import java.util.List;

public interface Repository<T, ID extends Serializable> {
    T add(T object);

    T get(ID id);

    T update(T oldObject, T newObject);

    void remove(T object);

    List<T> getAll();

}
