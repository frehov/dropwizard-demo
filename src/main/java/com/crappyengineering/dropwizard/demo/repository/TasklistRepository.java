package com.crappyengineering.dropwizard.demo.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;

import com.crappyengineering.dropwizard.demo.model.Tasklist;

public class TasklistRepository {

    EntityManager entityManager;

    public TasklistRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<Tasklist> findById(@NotNull long id) {
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", entityManager.getEntityGraph("graph.Tasklist.tasks"));

        return Optional.ofNullable(entityManager.find(Tasklist.class, id, hints));
    }

    public Optional<List<Tasklist>> findAll() {
        return Optional.ofNullable(entityManager.createQuery("SELECT t from Tasklist t", Tasklist.class)
                .setHint("javax.persistence.fetchgraph", entityManager.getEntityGraph("graph.Tasklist.tasks"))
                .getResultList());
    }

    public Tasklist save(@NotNull Tasklist tasklist) {
        entityManager.persist(tasklist);
        return tasklist;
    }

    public int deleteById(@NotNull long id) {
        return entityManager.createQuery("DELETE FROM Tasklist WHERE id = :id")
                .setParameter("id", id)
                .executeUpdate();

        //findById(id).ifPresent(entityManager::remove);
    }

    public int update(@NotNull long id, @NotNull Tasklist tasklist) {
        return entityManager.createQuery("UPDATE Tasklist SET title = :title, owner = :owner where id = :id")
                .setParameter("title", tasklist.getTitle())
                .setParameter("owner", tasklist.getOwner())
                .setParameter("id", id)
                .executeUpdate();
    }
}
