package com.narnia.railways.dao;

import com.narnia.railways.model.Train;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class TrainDAO {

    private final SessionFactory sessionFactory;

    public TrainDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Train train) {
        sessionFactory.getCurrentSession().save(train);
    }

    public List<Train> list() {
        TypedQuery<Train> query = sessionFactory.getCurrentSession().createQuery("from Train");
        return query.getResultList();
    }

    public Train getById(Long id) {
        return sessionFactory.getCurrentSession().get(Train.class, id);
    }


    public void delete(Train train) {
        sessionFactory.getCurrentSession().delete(train);
    }

    public void update(Train train) {
        sessionFactory.getCurrentSession().update(train);
    }
}