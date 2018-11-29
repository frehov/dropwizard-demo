package com.crappyengineering.dropwizard.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.crappyengineering.dropwizard.demo.model.Tasklist;
import org.hibernate.SessionFactory;

public class TasklistRepositoryImpl implements TasklistRepository {

    SessionFactory sessionFactory;

    public TasklistRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Tasklist> findById(@NotNull long id) {
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", sessionFactory.getCurrentSession().getEntityGraph("graph.Tasklist.tasks"));

        return Optional.ofNullable(sessionFactory.getCurrentSession().find(Tasklist.class, id, hints));
    }

    @Override
    public Optional<List<Tasklist>> findAll() {
        return Optional.ofNullable(sessionFactory.getCurrentSession().createQuery("SELECT t from Tasklist t", Tasklist.class)
                .setHint("javax.persistence.fetchgraph", sessionFactory.getCurrentSession().getEntityGraph("graph.Tasklist.tasks"))
                .getResultList());
    }

    @Override
    public Tasklist save(@NotNull Tasklist tasklist) {
        sessionFactory.getCurrentSession().persist(tasklist);
        return tasklist;
    }

    @Override
    public int deleteById(@NotNull long id) {
        return sessionFactory.getCurrentSession().createQuery("DELETE FROM Tasklist WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();

        //findById(id).ifPresent(sessionFactory.getCurrentSession()::remove);
    }

    @Override
    public int update(@NotNull long id, @NotNull Tasklist tasklist) {
        return sessionFactory.getCurrentSession().createQuery("UPDATE Tasklist SET title = :title, owner = :owner where id = :id")
                .setParameter("title", tasklist.getTitle())
                .setParameter("owner", tasklist.getOwner())
                .setParameter("id", id)
                .executeUpdate();
    }
}
