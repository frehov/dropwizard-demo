package com.crappyengineering.dropwizard.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import com.crappyengineering.dropwizard.demo.model.Task;

public class TaskRepository {

    EntityManager entityManager;

    public TaskRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Task> findById(@NotNull long id) {
        return Optional.ofNullable(entityManager.find(Task.class, id));
    }

    public Optional<List<Task>> findAll() {
        return Optional.ofNullable(entityManager.createQuery("SELECT t from Task t", Task.class).getResultList());
    }

    public Task save(@NotNull Task task) {
        entityManager.persist(task);
        return task;
    }

    public int deleteById(@NotNull long id) {
        return entityManager.createQuery("DELETE FROM Task WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();

        //findById(id).ifPresent(entityManager::remove);
    }

    public int update(@NotNull long id, @NotNull Task task) {
        return entityManager.createQuery("UPDATE Task SET description = :description, parent = :parent, status = :status WHERE id = :id")
                .setParameter("description", task.getDescription())
                .setParameter("parent", task.getParent())
                .setParameter("status", task.getStatus())
                .setParameter("id", id)
                .executeUpdate();
    }
}
