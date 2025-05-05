package com.wesleybertipaglia.controllers;

import java.net.URI;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.wesleybertipaglia.services.ResourceService;

import io.quarkus.security.identity.SecurityIdentity;
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

    @Inject
    SecurityIdentity identity;

    @ConfigProperty(name = "app.frontend.url")
    String frontendUrl;

    @GET
    @Path("/{slug}")
    public Response redirect(@PathParam("slug") String slug) {
        if (identity.isAnonymous()) {
            var redirectUrl = frontendUrl + "/signin?redirect=/s/" + slug;
            return Response.seeOther(URI.create(redirectUrl)).build();
        }

        var resource = resourceService.getBySlug(slug);

        var url = resource.url();
        if (!url.matches("^(http|https)://.*")) {
            url = "https://" + url;
        }

        return Response.status(Response.Status.OK).entity(url).build();
    }
}
