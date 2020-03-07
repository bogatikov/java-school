package com.narnia.railways.dao;

import com.narnia.railways.model.Passenger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class PassengerDAO {

    private final SessionFactory sessionFactory;

    public PassengerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Passenger passenger) {
        sessionFactory.getCurrentSession().save(passenger);
    }

    public List<Passenger> list() {
        TypedQuery<Passenger> query = sessionFactory.getCurrentSession().createQuery("from Passenger");
        return query.getResultList();
    }

    public Passenger getById(Long id) {
        return sessionFactory.getCurrentSession().get(Passenger.class, id);
    }


    public void delete(Passenger passenger) {
        sessionFactory.getCurrentSession().delete(passenger);
    }

    public void update(Passenger passenger) {
        sessionFactory.getCurrentSession().update(passenger);
    }
}
