package com.wesleybertipaglia.controllers;

import com.wesleybertipaglia.dtos.UserDtos;
import com.wesleybertipaglia.services.ProfileService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/api/profile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileController {
    @Inject
    ProfileService profileService;

    @GET
    public Response get() {
        var user = profileService.get();
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @PUT
    public Response update(@Valid UserDtos.Update dto) {
        var user = profileService.update(dto);
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @DELETE
    public Response delete() {
        profileService.delete();
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}