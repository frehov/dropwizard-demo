package com.crappyengineering.dropwizard.demo.resource;


import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.crappyengineering.dropwizard.demo.repository.TaskRepository;
import com.crappyengineering.dropwizard.demo.repository.TasklistRepository;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/tasklist")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TasklistResource {

    private final TasklistRepository tasklistRepository;

    public TasklistResource(TasklistRepository tasklistRepository) {
        this.tasklistRepository = tasklistRepository;
    }

    @GET
    @Timed
    @UnitOfWork(readOnly = true)
    public Response getAllTaskslists() {
        return tasklistRepository.findAll().map(taskslists -> ok(taskslists).build()).orElseGet(noContent()::build);
    }
}
