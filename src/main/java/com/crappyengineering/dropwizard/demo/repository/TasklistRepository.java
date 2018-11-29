package com.crappyengineering.dropwizard.demo.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import com.crappyengineering.dropwizard.demo.model.Tasklist;

public interface TasklistRepository {
    Optional<Tasklist> findById(@NotNull long id);

    Optional<List<Tasklist>> findAll();

    Tasklist save(@NotNull Tasklist tasklist);

    int deleteById(@NotNull long id);

    int update(@NotNull long id, @NotNull Tasklist tasklist);
}
