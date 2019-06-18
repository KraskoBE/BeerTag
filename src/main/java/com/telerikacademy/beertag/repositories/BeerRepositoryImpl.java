package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Beer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository("BeerRepository")
public class BeerRepositoryImpl implements Repository<Beer> {
    private SessionFactory sessionFactory;

    @Autowired
    public BeerRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Beer add(Beer beer) {
        Session session = sessionFactory.getCurrentSession();
        session.save(beer);
        return beer;
    }

    @Override
    public Beer get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Beer.class, id);
    }

    @Override
    public Beer update(Beer oldBeer, Beer newBeer) {
        Session session = sessionFactory.getCurrentSession();Transaction transaction = session.beginTransaction();

        oldBeer.setName(newBeer.getName());
        oldBeer.setBrewery(newBeer.getBrewery());
        oldBeer.setOriginCountry(newBeer.getOriginCountry());
        oldBeer.setABV(newBeer.getABV());
        oldBeer.setDescription(newBeer.getDescription());
        oldBeer.setType(newBeer.getType());
        oldBeer.setStyle(newBeer.getStyle());

        session.update(oldBeer);
        transaction.commit();
        return oldBeer;
    }

    @Override
    public void remove(Beer beer) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(beer);
        transaction.commit();
    }

    @Override
    public List<Beer> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Beer> query = session.createQuery("from Beer", Beer.class);
        return query.list();
    }
}
