package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Beer;
import com.telerikacademy.beertag.models.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository("BeerRepository")
public class BeerRepositoryImpl implements Repository<Beer> {
    private SessionFactory sessionFactory;
    private Repository<Tag> tagRepository;

    @Autowired
    public BeerRepositoryImpl(SessionFactory sessionFactory, Repository<Tag> tagRepository) {
        this.sessionFactory = sessionFactory;
        this.tagRepository = tagRepository;
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
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        oldBeer.setName(newBeer.getName());
        oldBeer.setBrewery(newBeer.getBrewery());
        oldBeer.setOriginCountry(newBeer.getOriginCountry());
        oldBeer.setABV(newBeer.getABV());
        oldBeer.setDescription(newBeer.getDescription());
        oldBeer.setType(newBeer.getType());
        oldBeer.setStyle(newBeer.getStyle());
        oldBeer.getBeerTags().add(tagRepository.get(1));
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
