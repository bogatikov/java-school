package com.narnia.railways.dao;

import com.narnia.railways.model.Ticket;
import com.narnia.railways.model.Train;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@Transactional
public class TicketDAO {

    private final SessionFactory sessionFactory;

    public TicketDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Ticket ticket) {
        sessionFactory.getCurrentSession().save(ticket);
    }

    public List<Ticket> list() {
        TypedQuery<Ticket> query = sessionFactory.getCurrentSession().createQuery("from Ticket");
        return query.getResultList();
    }

    public Ticket getById(Long id) {
        return sessionFactory.getCurrentSession().get(Ticket.class, id);
    }


    public void delete(Ticket ticket) {
        sessionFactory.getCurrentSession().delete(ticket);
    }

    public void update(Ticket ticket) {
        sessionFactory.getCurrentSession().update(ticket);
    }

    public List<Ticket> getActiveTicketList() {
        TypedQuery<Ticket> query = sessionFactory.getCurrentSession().createQuery("from Ticket t where t.active = true");
        return query.getResultList();
    }

    public List<Ticket> getActiveTicketList(Train train) {
        TypedQuery<Ticket> query = sessionFactory.getCurrentSession()
                .createQuery("from Ticket t where t.active = true and t.train = :train");
        query.setParameter("train", train);
        return query.getResultList();
    }
}
