package com.poixen.java.api.izserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.poixen.java.api.izserver.model.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * Custom resource for checking login times
 */
@Path("/")
public class LoginLogsResource {

    @GET
    @Timed
    @RolesAllowed({"USER"})
    @Path("/successful_logins")
    public String[] successfulLogins(@Context SecurityContext context) {

        User user = (User)context.getUserPrincipal();
        if (user == null) {
            return null;
        }

        return user.getSuccessfulLogins();
    }


    @GET
    @Timed
    @RolesAllowed({"USER"})
    @Path("/failed_logins")
    public String[] failedLogins(@Context SecurityContext context) {
        // user needs to pass in their token

        User user = (User)context.getUserPrincipal();
        if (user == null) {
            return null;
        }

        return user.getFailedLogins();
    }
}
