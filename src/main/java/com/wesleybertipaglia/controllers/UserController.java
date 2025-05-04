package com.wesleybertipaglia.controllers;

import org.bson.types.ObjectId;

import com.wesleybertipaglia.services.UserService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/api/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController {
    @Inject
    UserService userService;

    @GET
    public Response list(
            @QueryParam("page") @DefaultValue("0") Integer page,
            @QueryParam("size") @DefaultValue("10") Integer size) {
        var users = userService.list(page, size);
        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") String id) {
        var user = userService.getDetailsById(new ObjectId(id));
        return Response.ok(user).build();
    }
}
