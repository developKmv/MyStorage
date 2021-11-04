package ru.develop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.develop.entity.Storage;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class StorageDAOImpl implements StorageDAO{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void saveStorage(Storage st) {
        Session session = sessionFactory.getCurrentSession();
        session.save(st);
    }

    @Override
    @Transactional
    public List<Storage> getAllStorage() {
        Session session = sessionFactory.getCurrentSession();
        Query<Storage> query = session.createQuery("from Storage",Storage.class);
        List<Storage> rs = query.getResultList();
        return rs;
    }

    @Override
    @Transactional
    public Storage getStorage(int id) {
        Session session = sessionFactory.getCurrentSession();
        Storage storage = session.get(Storage.class,id);

        return storage;
    }

    @Override
    @Transactional
    public void deleteStorage(int id) {
        Session session = sessionFactory.getCurrentSession();
        Storage storage = session.get(Storage.class,id);
        session.delete(storage);
    }
}
