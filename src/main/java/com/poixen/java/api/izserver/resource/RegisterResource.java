package com.poixen.java.api.izserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.poixen.java.api.izserver.model.User;
import com.poixen.java.api.izserver.model.dao.UserDAO;
import com.poixen.java.api.izserver.model.request.RegisterRequest;
import com.poixen.java.api.izserver.model.request.validators.RequestValidator;

import javax.annotation.security.PermitAll;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

/**
 * Custom resource for registering a new user
 */
@Path("/register")
@Produces(MediaType.APPLICATION_JSON)
public class RegisterResource {

    private final RequestValidator requestValidator;
    private final UserDAO userDAO;

    public RegisterResource(final RequestValidator requestValidator, final UserDAO userDAO) {
        this.requestValidator = requestValidator;
        this.userDAO = userDAO;
    }

    /**
     * Method has {@link PermitAll} for user roles.
     * Any user should be able to perform a registration action.
     * In a later version you might want to add IP blocking if a user
     * is performing too many requests.
     * <p>
     * Performs validation of the provided username and password, and
     * will enter into the database with the "USER" role.
     *
     * @return a {@link Response} of the outcome.
     */
    @POST
    @Timed
    public Response resgister(final RegisterRequest request, @Context UriInfo uriInfo) {

        // validate the incoming request
        requestValidator.validate(request);

        // does username already exist?
        User user = userDAO.findExistingUser(request.getUsername());
        if (user != null) {
            // can be padded out
            return Response.status(409).build();
        }

        // add user to db with USER role
        userDAO.insert(request.getUsername(), request.getPassword(), request.getName(), request.getAge(), "{}");

        // close the dao
        userDAO.close();

        // return a 201 created, can be padded out
        return Response.status(201).build();
    }

}
