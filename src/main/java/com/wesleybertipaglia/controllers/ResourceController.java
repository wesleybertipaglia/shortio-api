package com.wesleybertipaglia.controllers;

import com.wesleybertipaglia.dtos.ResourceDtos;
import com.wesleybertipaglia.services.ResourceService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/resources")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResourceController {
    @Inject
    ResourceService resourceService;

    @GET
    public Response list(@QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("10") Integer size) {
        var resources = resourceService.list(page, size);
        return Response.status(Response.Status.OK).entity(resources).build();
    }

    @GET
    @Path("/{id}")
    public Response get(@PathParam("id") String id) {
        ResourceDtos.Details resource = resourceService.getById(id);
        return Response.status(Response.Status.OK).entity(resource).build();
    }

    @POST
    public Response create(ResourceDtos.Create resourceDto) {
        var resource = resourceService.create(resourceDto);
        return Response.status(Response.Status.CREATED).entity(resource).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") String id, ResourceDtos.Update resourceDto) {
        var resource = resourceService.update(id, resourceDto);
        return Response.status(Response.Status.OK).entity(resource).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        resourceService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}