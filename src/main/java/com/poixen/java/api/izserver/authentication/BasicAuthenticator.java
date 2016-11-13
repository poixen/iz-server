package com.poixen.java.api.izserver.authentication;

import com.poixen.java.api.izserver.model.User;
import com.poixen.java.api.izserver.model.dao.UserDAO;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

/**
 * {@link Authenticator} for handling Basic Credentials Authentication
 */
public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {

    private final UserDAO userDAO;

    public BasicAuthenticator(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Handles {@link BasicCredentials} Authentication
     *
     * @param basicCredentials username and password as {@link BasicCredentials}
     * @return an {@link Optional} that can contain the {@link User} if authenticated.
     * @throws AuthenticationException thrown if authentication
     */
    public Optional<User> authenticate(final BasicCredentials basicCredentials) throws AuthenticationException {
        User user = userDAO.getUser(basicCredentials.getUsername(), basicCredentials.getPassword());
        return user == null ? Optional.empty() : Optional.of(user);
    }
}
