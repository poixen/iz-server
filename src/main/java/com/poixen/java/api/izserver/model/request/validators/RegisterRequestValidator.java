package com.poixen.java.api.izserver.model.request.validators;

import com.poixen.java.api.izserver.model.request.RegisterRequest;
import com.poixen.java.api.izserver.model.request.Request;

/**
 * Custom {@link RequestValidator} class for validating a {@link com.poixen.java.api.izserver.model.request.RegisterRequest}
 */
public class RegisterRequestValidator implements RequestValidator {

    private final int minPasswordLen;
    private final int maxPasswordLen;
    private final int minUsernameLen;
    private final int maxUsernameLen;

    public RegisterRequestValidator(final int minPasswordLen, final int maxPasswordLen,
                                    final int minUsernameLen, final int maxUsernameLen) {
        this.minPasswordLen = minPasswordLen;
        this.maxPasswordLen = maxPasswordLen;
        this.minUsernameLen = minUsernameLen;
        this.maxUsernameLen = maxUsernameLen;
    }

    @Override
    public void validate(final Request request) throws IllegalArgumentException {

        if (request instanceof RegisterRequest) {
            RegisterRequest registerRequest = (RegisterRequest)request;

            // validate username length
            int usernameLen = registerRequest.getUsername().length();
            if (usernameLen < minUsernameLen || usernameLen > maxUsernameLen) {
                throw new IllegalArgumentException("Username must be between " + minPasswordLen + " and " + maxPasswordLen + " characters. Current: " + usernameLen);
            }

            // validate password length
            int passwordLen = registerRequest.getPassword().length();
            if (passwordLen < minPasswordLen || passwordLen > maxPasswordLen) {
                throw new IllegalArgumentException("Password must be between " + minPasswordLen + " and " + maxPasswordLen + " characters. Current: " + usernameLen);
            }

            // TODO: 14/11/16 add additional validation here if needed
            // add any additional validation needed here
            //  . check if sanitization is needed
            //  . check for invalid characters
            //  . check against any other configuration settings
            //  . and so on..

        } else {
            throw new IllegalArgumentException("Can not validate a request of type '" + request.getClass().getName() + "' in RegisterRequestValidator");
        }
    }
}
