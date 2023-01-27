package com.example.fullstack.task;

import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/tasks")
public class TaskResource {

    private final TaskService taskService;

    @Inject
    public TaskResource(TaskService taskService) {
        this.taskService = taskService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Uni<List<Task>> get() {
        return taskService.listForUser();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    @RolesAllowed("user")
    public Uni<Task> create(Task task) {
        return taskService.createTask(task);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Uni<Task> get(@PathParam("id") long id) {
        return taskService.findById(id);
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Uni<Task> update(@PathParam("id") long id, Task task) {
        task.id = id;
        return taskService.updateTask(task);
    }

    @PUT
    @Path("{id}/complete")
    @Consumes
    @Produces
    @RolesAllowed("user")
    public Uni<Boolean> setComplete(@PathParam("id") long id, boolean complete) {
        return taskService.setComplete(id, complete);
    }

    @DELETE
    @Path("{id}")
    @Produces
    @RolesAllowed("user")
    public Uni<Void> delete(@PathParam("id") long id) {
        return taskService.deleteTask(id);
    }
}
