package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository("UserRepository")
public class UserRepositoryImpl implements Repository<User> {
    private SessionFactory sessionFactory;
    private List<User> users;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        users = new ArrayList<>();
    }

    @Override
    public User add(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user;
    }

    @Override
    public User get(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, id);
    }

    @Override
    public User update(User oldUser, User newUser) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        oldUser.setAge(newUser.getAge());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setName(newUser.getName());
        oldUser.setPassword(oldUser.getPassword());
        session.update(oldUser);
        transaction.commit();
        return oldUser;
    }

    @Override
    public User remove(User user) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(user);
        transaction.commit();
        return user;
    }

    @Override
    public List<User> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("FROM User", User.class);
        return query.list();
    }
}
