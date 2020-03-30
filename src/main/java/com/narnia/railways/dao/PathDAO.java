package com.narnia.railways.dao;

import com.narnia.railways.model.Path;
import com.narnia.railways.model.Station;
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

    public List<Path> getActivePath() {
        TypedQuery<Path> query = sessionFactory.getCurrentSession()
                .createQuery("from Path p where p.trains.size > 0");
        return query.getResultList();
    }

    public Path getPathByStations(Station a, Station b) {
        TypedQuery<Path> query = sessionFactory.getCurrentSession()
                .createQuery("from Path p where p.f_node = :a and p.s_node = :b or p.f_node = :b and p.s_node = :a");
        query.setParameter("a", a);
        query.setParameter("b", b);
        return query.getSingleResult();
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
