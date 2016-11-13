package com.poixen.java.api.izserver.authentication;

import com.poixen.java.api.izserver.model.User;
import io.dropwizard.auth.Authorizer;

import java.util.Arrays;

/**
 * Authorizes users bases on their roles
 */
public class BasicAuthorizer implements Authorizer<User> {

    public boolean authorize(User principal, String role) {
        // TODO: 12/11/16 cheap way to validate role
        return Arrays.asList(principal.getRoles()).contains("USER");
    }
}
