package com.example.fullstack.project;

import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.ResponseStatus;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api/v1/projects")
public class ProjectResource {
    private final ProjectService projectService;

    @Inject
    public ProjectResource(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    public Uni<List<Project>> get() {
        return projectService.listForUser();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @ResponseStatus(201)
    public Uni<Project> createProject(Project project) {
        return projectService.createProject(project);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("{id}")
    public Uni<Project> get(@PathParam("id") long id) {
        return projectService.findById(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("{id}")
    public Uni<Project> update(@PathParam("id") long id, Project project) {
        project.id = id;
        return projectService.updateProject(project);
    }

    @DELETE
    @Produces
    @RolesAllowed("user")
    @Path("{id}")
    public Uni<Void> delete(@PathParam("id") long id) {
        return projectService.deleteProject(id);
    }
}
