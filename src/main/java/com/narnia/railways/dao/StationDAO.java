package com.narnia.railways.dao;

import com.narnia.railways.model.Station;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class StationDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Station station) {
        sessionFactory.getCurrentSession().save(station);
    }

    public List<Station> list() {
        TypedQuery<Station> query = sessionFactory.getCurrentSession().createQuery("from Station");
        return query.getResultList();
    }

    public Station getById(Long id) {
        return sessionFactory.getCurrentSession().get(Station.class, id);
    }


    public void delete(Station station) {
        sessionFactory.getCurrentSession().delete(station);
    }

    public void update(Station station) {
        sessionFactory.getCurrentSession().update(station);
    }
}
