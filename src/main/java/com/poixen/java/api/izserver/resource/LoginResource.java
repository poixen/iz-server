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
import java.util.*;

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

        Map<String, Object> entity = new HashMap<>();

        // validate incoming request
        try{
            requestValidator.validate(request);
        } catch (Exception e) {
            // TODO: 12/11/16 can be expanded upon if needed
            entity.put("code", "400");
            entity.put("reason", e.getMessage());
            return Response.status(400).entity(entity).build();
        }

        // get the user
        User user = userDAO.getUser(request.getUsername());
        if (user == null) {
            entity.put("code", "401");
            entity.put("reason", "wrong username or password");
            return Response.status(401).entity(entity).build();
        }
        // check password
        if (!user.getPassword().equals(DigestUtils.sha1Hex(request.getPassword()))) {
            userDAO.addFailedLogin(new Date().toString(), user.getUsername());
            entity.put("code", "401");
            entity.put("reason", "wrong username or password");
            return Response.status(401).entity(entity).build();
        }

        // add a timestamp to the successful login
        userDAO.addSuccessfulLogin(new Date().toString(), request.getUsername());

        String pretoken = user.getUsername() + ":" + user.getPassword();
        String token = new String(Base64.encodeBase64(pretoken.getBytes()));
        entity.put("code", "200");
        entity.put("token", token);

        // TODO: 12/11/16 can return an auth token to be used, for now we return the user to see something
        return Response.status(200).entity(entity).build();
    }
}
