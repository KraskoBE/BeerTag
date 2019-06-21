package com.telerikacademy.beertag.repositories;

import com.telerikacademy.beertag.models.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Repository("ImageRepository")
public class ImageRepositoryImpl implements Repository<Image, Integer> {
    private SessionFactory sessionFactory;

    @Autowired
    public ImageRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Image add(Image image) {
        Session session = sessionFactory.getCurrentSession();
        session.save(image);
        return image;
    }

    @Override
    public Image get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Image.class, id);
    }

    @Override
    public Image update(Image oldImage, Image newImage) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        oldImage.setBytes(newImage.getBytes());

        transaction.commit();
        return oldImage;
    }

    @Override
    public void remove(Image image) {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.remove(image);
        transaction.commit();
    }

    @Override
    public List<Image> getAll() {
        Session session = sessionFactory.getCurrentSession();
        Query<Image> query = session.createQuery("from Image", Image.class);
        return query.list();
    }

    /*public Image updateByBytes(Image image, byte[] bytes)
    {
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        image.setBytes(bytes);

        transaction.commit();
        return image;
    }*/
}
