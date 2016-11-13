package com.poixen.java.api.izserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.poixen.java.api.izserver.model.dao.UserDAO;
import com.poixen.java.api.izserver.model.request.LoginRequest;
import com.poixen.java.api.izserver.model.request.validators.RequestValidator;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Custom resource for logging in wih an existing user
 */
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

    private final RequestValidator requestValidator;
    private final UserDAO userDAO;

    public LoginResource(final RequestValidator requestValidator, final UserDAO userDAO) {
        this.requestValidator = requestValidator;
        this.userDAO = userDAO;
    }


    @POST
    @Timed
    @RolesAllowed({"USER"})
    public Response login(final LoginRequest request) {

        // validate incoming request
        try{
            requestValidator.validate(request);
        } catch (Exception e) {
            // TODO: 12/11/16 can be expanded upon if needed
            return Response.status(400).entity(e.getMessage()).build();
        }

        // TODO: 12/11/16 what if the password was wrong need to add to failed_logins

        // add a timestamp to the successful login
        userDAO.updateSuccessfulLogin(request.getUsername());

        // TODO: 12/11/16 can return a token to be used
        return Response.status(200).build();
    }
}
