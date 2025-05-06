package com.wesleybertipaglia.pages;

import java.net.URI;

import com.wesleybertipaglia.services.ResourceService;

import io.quarkus.security.identity.SecurityIdentity;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/s")
@Consumes(MediaType.APPLICATION_JSON)
public class ShortPageController {
    @Inject
    ResourceService resourceService;

    @Inject
    SecurityIdentity identity;

    @GET
    @Path("/{slug}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response redirect(@PathParam("slug") String slug) {
        if (identity.isAnonymous()) {
            var url = "/p/auth/signin?redirect=" + slug;
            return Response.seeOther(URI.create(url)).build();
        }

        var resource = resourceService.getBySlug(slug);
        var url = resource.url();

        if (!url.matches("^(http|https)://.*")) {
            url = "https://" + url;
        }

        return Response.ok(new JsonObject().put("url", url)).build();
    }
}
