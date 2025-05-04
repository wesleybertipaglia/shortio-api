package com.wesleybertipaglia.controllers;

import java.net.URI;

import com.wesleybertipaglia.services.ResourceService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/s")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ShortController {
    @Inject
    ResourceService resourceService;

    @GET
    @Path("/{slug}")
    public Response redirect(@PathParam("slug") String slug) {
        var resource = resourceService.getBySlug(slug);
        var url = resource.url();

        if (!url.matches("^(http|https)://.*")) {
            url = "https://" + url;
        }

        return Response.seeOther(URI.create(url)).build();
    }
}