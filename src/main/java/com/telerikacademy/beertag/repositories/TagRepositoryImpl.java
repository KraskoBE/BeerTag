package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository("TagRepository")
public class TagRepositoryImpl implements Repository<Tag, Integer> {
    @Autowired
    public TagRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private SessionFactory sessionFactory;

    @Override
    public Tag add(Tag tag) {
        Session session = sessionFactory.getCurrentSession();
        tag.setName(tag.getName().toLowerCase());
        session.save(tag);
        return tag;
    }

    @Override
    public Tag get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Tag.class, id);
    }

    @Override
    public Tag update(Tag oldTag, Tag newTag) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.update(oldTag);
        transaction.commit();
        return oldTag;
    }

    @Override
    public void remove(Tag tag) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(tag);
        transaction.commit();
    }

    @Override
    public List<Tag> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Tag> query = session.createQuery("from Tag", Tag.class);
        return query.list();
    }
}
