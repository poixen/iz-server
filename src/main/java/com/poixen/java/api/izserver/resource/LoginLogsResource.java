package com.poixen.java.api.izserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.poixen.java.api.izserver.model.User;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Custom resource for checking login times
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class LoginLogsResource {

    @GET
    @Timed
    @RolesAllowed({"USER"})
    @Path("/successful_logins")
    public Response successfulLogins(@Context SecurityContext context) {

        User user = (User)context.getUserPrincipal();
        if (user == null) {
            return null;
        }

        Map<String, Object> entity = new HashMap<>();
        entity.put("successful_logins", getLogins(user.getSuccessfulLogins(), 5));

        return Response.ok().entity(entity).build();
    }


    @GET
    @Timed
    @RolesAllowed({"USER"})
    @Path("/failed_logins")
    public Response failedLogins(@Context SecurityContext context) {
        // user needs to pass in their token

        User user = (User)context.getUserPrincipal();
        if (user == null) {
            return null;
        }

        Map<String, Object> entity = new HashMap<>();
        entity.put("failed_logins", getLogins(user.getFailedLogins(), 5));

        return Response.ok().entity(entity).build();
    }


    private List<String> getLogins(final List<String> logins, final int limit) {
        if (logins.size() >= limit) {
            return logins.subList(logins.size() - limit, logins.size());
        }
        return logins;
    }
}
