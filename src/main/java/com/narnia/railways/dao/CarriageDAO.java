package com.narnia.railways.dao;

import com.narnia.railways.model.Carriage;
import com.narnia.railways.model.Train;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class CarriageDAO {

    private final SessionFactory sessionFactory;

    public CarriageDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Carriage carriage) {
        sessionFactory.getCurrentSession().save(carriage);
    }

    public List<Carriage> findAll() {
        TypedQuery<Carriage> query = sessionFactory.getCurrentSession().createQuery("from Carriage ");
        return query.getResultList();
    }

    public Carriage getById(Long id) {
        return sessionFactory.getCurrentSession().get(Carriage.class, id);
    }


    public void delete(Carriage carriage) {
        sessionFactory.getCurrentSession().delete(carriage);
    }

    public void update(Carriage carriage) {
        sessionFactory.getCurrentSession().update(carriage);
    }

    public List<Carriage> getCarriagesByTrain(Train train) {
        TypedQuery<Carriage> query = sessionFactory.getCurrentSession().createQuery("from Carriage c where c.train = :train");
        query.setParameter("train", train);
        return query.getResultList();
    }

    public Carriage getCarriageWithFreePlace(Train train) {
        TypedQuery<Carriage> query = sessionFactory.getCurrentSession()
                .createQuery("from Carriage c where c.train = :train and c.capacity > 0")
                .setMaxResults(1)
                .setParameter("train", train)
                .setLockMode(LockModeType.PESSIMISTIC_READ);
        return query.getSingleResult();
    }
}