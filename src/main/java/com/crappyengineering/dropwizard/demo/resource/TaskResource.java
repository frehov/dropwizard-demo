package com.crappyengineering.dropwizard.demo.resource;


import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.crappyengineering.dropwizard.demo.repository.TaskRepository;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/task")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TaskResource {

    private final TaskRepository taskRepository;

    public TaskResource(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GET
    @Timed
    @UnitOfWork(readOnly = true)
    public Response getAllTasks() {
        return taskRepository.findAll().map(tasks -> ok(tasks).build()).orElseGet(noContent()::build);
    }
}
