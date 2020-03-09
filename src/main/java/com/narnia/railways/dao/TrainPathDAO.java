package com.narnia.railways.dao;

import com.narnia.railways.model.Train;
import com.narnia.railways.model.TrainPath;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class TrainPathDAO {

    private final SessionFactory sessionFactory;

    public TrainPathDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(TrainPath trainPath) {
        sessionFactory.getCurrentSession().save(trainPath);
    }

    public List<TrainPath> list() {
        TypedQuery<TrainPath> query = sessionFactory.getCurrentSession().createQuery("from TrainPath");
        return query.getResultList();
    }

    public TrainPath getById(Long id) {
        return sessionFactory.getCurrentSession().get(TrainPath.class, id);
    }


    public void delete(TrainPath trainPath) {
        sessionFactory.getCurrentSession().delete(trainPath);
    }

    public void update(TrainPath trainPath) {
        sessionFactory.getCurrentSession().update(trainPath);
    }

    public List<TrainPath> getSortedPathForTrain(Train train) {
        TypedQuery<TrainPath> query = sessionFactory.getCurrentSession().createQuery("from TrainPath tp where tp.train = :train order by tp.path_order asc");
        query.setParameter("train", train);
        return query.getResultList();
    }
}