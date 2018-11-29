package com.crappyengineering.dropwizard.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.crappyengineering.dropwizard.demo.model.Task;
import org.hibernate.SessionFactory;

public class TaskRepositoryImpl implements TaskRepository {

    SessionFactory sessionFactory;

    public TaskRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Task> findById(@NotNull long id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(Task.class, id));
    }

    @Override
    public Optional<List<Task>> findAll() {
        return Optional.ofNullable(sessionFactory.getCurrentSession().createQuery("SELECT t from Task t", Task.class).getResultList());
    }

    @Override
    public Task save(@NotNull Task task) {
        sessionFactory.getCurrentSession().persist(task);
        return task;
    }

    @Override
    public int deleteById(@NotNull long id) {
        return sessionFactory.getCurrentSession().createQuery("DELETE FROM Task WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();

        //findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public int update(@NotNull long id, @NotNull Task task) {
        return sessionFactory.getCurrentSession().createQuery("UPDATE Task SET description = :description, parent = :parent, status = :status WHERE id = :id")
                .setParameter("description", task.getDescription())
                .setParameter("parent", task.getParent())
                .setParameter("status", task.getStatus())
                .setParameter("id", id)
                .executeUpdate();
    }
}
