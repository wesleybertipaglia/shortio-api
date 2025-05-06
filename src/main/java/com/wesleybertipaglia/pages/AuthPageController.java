package com.wesleybertipaglia.pages;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

@Path("/p/auth")
public class AuthPageController {
    @Inject
    Template signin;

    @Inject
    Template signup;

    @GET
    @Path("/signin")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance signinPage(@QueryParam("redirect") @DefaultValue("") String redirect) {
        return signin.data("redirect", redirect);
    }

    @GET
    @Path("/signup")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance signupPage(@QueryParam("redirect") @DefaultValue("") String redirect) {
        return signup.data("redirect", redirect);
    }
}