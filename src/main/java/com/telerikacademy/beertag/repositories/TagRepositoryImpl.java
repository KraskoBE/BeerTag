package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
@org.springframework.stereotype.Repository("TagRepository")
public class TagRepositoryImpl implements Repository<Tag>{
    public TagRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private SessionFactory sessionFactory;


    @Override
    public Tag add(Tag tag) {
        Session session = sessionFactory.getCurrentSession();
        session.save(tag);
        return tag;
    }

    @Override
    public Tag get(int id) {
        return null;
    }

    @Override
    public Tag update(Tag oldTag, Tag newTag) {
        return null;
    }

    @Override
    public void remove(Tag tag) {

    }

    @Override
    public List<Tag> getAll() {
        return null;
    }
}
