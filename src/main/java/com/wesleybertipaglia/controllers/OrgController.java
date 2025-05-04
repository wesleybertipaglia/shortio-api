package com.wesleybertipaglia.controllers;

import com.wesleybertipaglia.dtos.OrgDtos;
import com.wesleybertipaglia.services.OrgService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/api/org")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrgController {
    @Inject
    OrgService orgService;

    @GET
    public Response get() {
        var org = orgService.get();
        return Response.status(Response.Status.OK).entity(org).build();
    }

    @PUT
    public Response update(OrgDtos.Update dto) {
        var org = orgService.update(dto);
        return Response.status(Response.Status.OK).entity(org).build();
    }
}
