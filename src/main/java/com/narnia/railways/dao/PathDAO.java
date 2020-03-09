package com.narnia.railways.dao;

import com.narnia.railways.model.Path;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class PathDAO {

    private final SessionFactory sessionFactory;

    public PathDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Path path) {
        sessionFactory.getCurrentSession().save(path);
    }

    public List<Path> list() {
        TypedQuery<Path> query = sessionFactory.getCurrentSession().createQuery("from Path");
        return query.getResultList();
    }

    public Path getById(Long id) {
        return sessionFactory.getCurrentSession().get(Path.class, id);
    }


    public void delete(Path path) {
        sessionFactory.getCurrentSession().delete(path);
    }

    public void update(Path path) {
        sessionFactory.getCurrentSession().update(path);
    }
}
