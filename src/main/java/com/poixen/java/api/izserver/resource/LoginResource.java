package com.poixen.java.api.izserver.resource;

import com.codahale.metrics.annotation.Timed;
import com.poixen.java.api.izserver.model.User;
import com.poixen.java.api.izserver.model.dao.UserDAO;
import com.poixen.java.api.izserver.model.request.LoginRequest;
import com.poixen.java.api.izserver.model.request.validators.RequestValidator;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public Response login(final LoginRequest request) {

        // validate incoming request
        try{
            requestValidator.validate(request);
        } catch (Exception e) {
            // TODO: 12/11/16 can be expanded upon if needed
            return Response.status(400).entity(e.getMessage()).build();
        }

        // get the user
        User user = userDAO.getUser(request.getUsername());
        if (user == null) {
            return Response.status(401).entity("Wrong username or password").build();
        }
        // check password
        if (!user.getPassword().equals(DigestUtils.sha1Hex(request.getPassword()))) {
            userDAO.addFailedLogin(user.getUsername(), new Date().toString());
        }


        // TODO: 13/11/16 took too long to try and figure out how to append to an array of varchar, making 2 calls for this example
        // add a timestamp to the successful login
        userDAO.addSuccessfulLogin(request.getUsername(), new Date().toString());

        String pretoken = user.getUsername() + ":" + user.getPassword();
        String token = new String(Base64.encodeBase64(pretoken.getBytes()));
        Map<String, String> entity = new HashMap<>();
        entity.put("token", token);

        // TODO: 12/11/16 can return an auth token to be used, for now we return the user to see something
        return Response.status(200).entity(entity).build();
    }
}
