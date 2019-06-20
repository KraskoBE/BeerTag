package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.Country;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository("CountryRepository")
public class CountryRepositoryImpl implements Repository<Country> {
    private SessionFactory sessionFactory;

    @Autowired
    public CountryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Country add(Country country) {
        Session session = sessionFactory.getCurrentSession();
        session.save(country);
        return country;
    }

    @Override
    public Country get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Country.class, id);
    }

    @Override
    public Country update(Country oldCountry, Country newCountry) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        oldCountry.setName(newCountry.getName());

        session.update(oldCountry);
        transaction.commit();
        return oldCountry;
    }

    @Override
    public Country remove(Country country) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(country);
        transaction.commit();
        return country;
    }

    @Override
    public List<Country> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Country> query = session.createQuery("from Country ", Country.class);
        return query.list();
    }
}
