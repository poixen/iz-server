package com.poixen.java.api.izserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.poixen.java.api.izserver.model.User;
import com.poixen.java.api.izserver.model.dao.UserDAO;
import com.poixen.java.api.izserver.model.request.RegisterRequest;
import com.poixen.java.api.izserver.model.request.validators.RequestValidator;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response register(final RegisterRequest request) {

        // validate the incoming request
        try {
            requestValidator.validate(request);
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }

        // check for existing username
        // TODO: 12/11/16 can add guava cache for user names to save db calls
        User user = userDAO.findExistingUser(request.getUsername());
        if (user != null) {
            return Response.status(409).build();
        }

        // add user to db with USER role
        // TODO: 12/11/16 in prod would need to S/P/Hash the password
        userDAO.insert(request.getUsername(), request.getPassword(), request.getName(), request.getAge());

        // close the dao
        userDAO.close();

        // return a 201 created
        return Response.status(201).build();
    }

}
