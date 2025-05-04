package com.wesleybertipaglia.controllers;

import com.wesleybertipaglia.dtos.*;
import com.wesleybertipaglia.services.AuthService;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/api/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthController {
    @Inject
    AuthService authService;

    @POST
    @Path("/signup")
    public Response signUp(@Valid AuthDtos.SignUp dto) {
        var response = authService.signUp(dto);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @POST
    @Path("/signin")
    public Response signIn(@Valid AuthDtos.SignIn dto) {
        var response = authService.signIn(dto);
        return Response.status(Response.Status.OK).entity(response).build();
    }
}