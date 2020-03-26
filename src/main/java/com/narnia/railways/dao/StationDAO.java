package com.narnia.railways.dao;

import com.narnia.railways.model.Station;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class StationDAO {

    private final SessionFactory sessionFactory;

    public StationDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Station station) {
        sessionFactory.getCurrentSession().save(station);
    }

    public List<Station> list() {
        TypedQuery<Station> query = sessionFactory.getCurrentSession().createQuery("from Station");
        return query.getResultList();
    }

    /**
     * Active station is station which have at least one link with other station
     *
     * @return Active station list
     */
    public List<Station> getActiveStationList() {
        /**
         * select * from station where station.id in (select s_node_id from path union select f_node_id from path); hql doesn't support union
         * select * from station where station.id in (select s_node_id from path) or station.id in (select f_node_id from path);
         */
        TypedQuery<Station> query = sessionFactory
                .getCurrentSession()
                .createQuery("from Station st where st in (select p.f_node from Path p) or st in (select p.s_node from Path p)");
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
