package com.poixen.java.api.izserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.poixen.java.api.izserver.model.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import java.util.List;

/**
 * Custom resource for checking login times
 */
@Path("/")
public class LoginLogsResource {

    @GET
    @Timed
    @RolesAllowed({"USER"})
    @Path("/successful_logins")
    public List<String> successfulLogins(@Context SecurityContext context) {

        User user = (User)context.getUserPrincipal();
        if (user == null) {
            return null;
        }

        return getLogins(user.getSuccessfulLogins(), 5);
    }


    @GET
    @Timed
    @RolesAllowed({"USER"})
    @Path("/failed_logins")
    public List<String> failedLogins(@Context SecurityContext context) {
        // user needs to pass in their token

        User user = (User)context.getUserPrincipal();
        if (user == null) {
            return null;
        }

        return getLogins(user.getFailedLogins(), 5);
    }


    private List<String> getLogins(final List<String> logins, final int limit) {
        if (logins.size() >= limit) {
            return logins.subList(logins.size() - limit, logins.size());
        }
        return logins;
    }
}
