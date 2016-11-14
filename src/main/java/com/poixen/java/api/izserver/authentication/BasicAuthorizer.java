package com.poixen.java.api.izserver.authentication;

import com.poixen.java.api.izserver.model.User;
import io.dropwizard.auth.Authorizer;

/**
 * Authorizes users bases on their roles
 */
public class BasicAuthorizer implements Authorizer<User> {

    public boolean authorize(User principal, String role) {
        return principal.getRoles().contains("USER");
    }
}
