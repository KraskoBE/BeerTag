package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.UserAuth;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("UserAuthRepository")
public class UserAuthRepositoryImpl implements com.telerikacademy.beertag.repositories.Repository<UserAuth, Integer> {
    private SessionFactory sessionFactory;

    @Autowired
    public UserAuthRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserAuth add(UserAuth userAuth) {
        Session session = sessionFactory.getCurrentSession();
        session.save(userAuth);
        return userAuth;
    }

    @Override
    public UserAuth get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(UserAuth.class, id);
    }

    @Override
    public UserAuth update(UserAuth oldUserAuth, UserAuth newUserAuth) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        oldUserAuth.setEmail(newUserAuth.getEmail());
        oldUserAuth.setPassword(newUserAuth.getPassword());

        transaction.commit();
        return oldUserAuth;

    }

    @Override
    public void remove(UserAuth userAuth) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.delete(userAuth);
        transaction.commit();
    }

    @Override
    public List<UserAuth> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<UserAuth> query = session.createQuery("FROM UserAuth", UserAuth.class);
        return query.list();
    }
}
