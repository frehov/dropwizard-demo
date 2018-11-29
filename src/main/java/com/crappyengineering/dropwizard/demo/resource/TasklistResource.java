package com.crappyengineering.dropwizard.demo.resource;


import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.created;
import static javax.ws.rs.core.Response.noContent;
import static javax.ws.rs.core.Response.ok;

import java.util.Optional;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.codahale.metrics.annotation.Timed;
import com.crappyengineering.dropwizard.demo.model.Tasklist;
import com.crappyengineering.dropwizard.demo.repository.TasklistRepository;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/api/tasklist")
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

    @POST
    @Timed
    @UnitOfWork
    public Response createNewTask(Tasklist tasklist, @Context UriInfo uriInfo) {
        return Optional.of(tasklistRepository.save(tasklist))
                .map(tasklist1 -> created(uriInfo.getRequestUriBuilder().path("/{id}").build(tasklist1.getId())).build())
                .orElseGet(() -> Response.status(BAD_REQUEST).build());
    }

    @GET
    @Timed
    @UnitOfWork(readOnly = true)
    @Path("/{id}")
    public Response findTaskById(@PathParam("id") long id) {
        return tasklistRepository.findById(id)
                .map(tasklist -> ok(tasklist).build())
                .orElseGet(() -> Response.status(NOT_FOUND).build());
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public Response updateTaskById(@PathParam("id") long id, Tasklist tasklist) {
        return tasklistRepository.update(id, tasklist) == 1 ? Response.ok().build() : Response.status(NOT_FOUND).build();
    }

    @DELETE
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public Response deleteTaskById(@PathParam("id") long id) {
        return tasklistRepository.deleteById(id) == 1 ? Response.ok().build() : Response.status(NOT_FOUND).build();
    }
}
