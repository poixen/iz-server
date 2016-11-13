package com.poixen.java.api.izserver.model.request.validators;

import com.poixen.java.api.izserver.model.request.LoginRequest;
import com.poixen.java.api.izserver.model.request.Request;

/**
 * Custom {@link RequestValidator} class for validating a {@link com.poixen.java.api.izserver.model.request.LoginRequest}
 */
public class LoginRequestValidator implements RequestValidator {

    @Override
    public void validate(Request request) throws IllegalArgumentException {
        if (request instanceof LoginRequest) {
            LoginRequest loginRequest = (LoginRequest)request;

            // validate username
            if (loginRequest.getUsername() == null || loginRequest.getUsername().isEmpty()) {
                throw new IllegalArgumentException("No username provided in Login Request");
            }

            // validate password
            // might be we want users to have empty password, for this example we do :)
            if (loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
                throw new IllegalArgumentException("No password provided in Login Request");
            }

            // validate more if needed...

        } else {
            throw new IllegalArgumentException("Can not validate a request of type '" + request.getClass().getName() + "' in RegisterRequestValidator");
        }
    }
}
