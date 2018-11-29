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
import com.crappyengineering.dropwizard.demo.model.Task;
import com.crappyengineering.dropwizard.demo.repository.TaskRepository;
import io.dropwizard.hibernate.UnitOfWork;

@Path("/api/task")
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
        return taskRepository.findAll()
                .map(tasks -> ok(tasks).build())
                .orElseGet(noContent()::build);
    }

    @POST
    @Timed
    @UnitOfWork
    public Response createNewTask(Task task, @Context UriInfo uriInfo) {
        return Optional.of(taskRepository.save(task))
                .map(task1 -> created(uriInfo.getRequestUriBuilder().path("/{id}").build(task1.getId())).build())
                .orElseGet(() -> Response.status(BAD_REQUEST).build());
    }

    @GET
    @Timed
    @UnitOfWork(readOnly = true)
    @Path("/{id}")
    public Response findTaskById(@PathParam("id") long id) {
        return taskRepository.findById(id)
                .map(task -> ok(task).build())
                .orElseGet(() -> Response.status(NOT_FOUND).build());
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public Response updateTaskById(@PathParam("id") long id, Task task) {
        return taskRepository.update(id, task) == 1 ? Response.ok().build() : Response.status(NOT_FOUND).build();
    }

    @DELETE
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public Response deleteTaskById(@PathParam("id") long id) {
        return taskRepository.deleteById(id) == 1 ? Response.ok().build() : Response.status(NOT_FOUND).build();
    }

}
