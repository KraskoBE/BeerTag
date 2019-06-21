package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.BeerRating;
import com.telerikacademy.beertag.models.BeerRatingId;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

@org.springframework.stereotype.Repository("BeerRatingRepository")
public class BeerRatingRepositoryImpl implements Repository<BeerRating, BeerRatingId> {

    private SessionFactory sessionFactory;

    public BeerRatingRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public BeerRating add(BeerRating beerRating) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(beerRating);
        transaction.commit();
        return beerRating;
    }

    @Override
    public BeerRating get(BeerRatingId id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(BeerRating.class, id);
    }

    @Override
    public BeerRating update(BeerRating oldBeerRating, BeerRating newBeerRating) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        oldBeerRating.setRating(newBeerRating.getRating());

        session.update(oldBeerRating);
        transaction.commit();
        return oldBeerRating;
    }

    @Override
    public void remove(BeerRating beerRating) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(beerRating);
        transaction.commit();
    }

    @Override
    public List<BeerRating> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<BeerRating> query = session.createQuery("from BeerRating ", BeerRating.class);
        return query.list();
    }
}
