package com.poixen.java.api.izserver.model.request.validators;

import com.poixen.java.api.izserver.model.request.Request;

/**
 * Interface that mast be implemented by all incoming {@link Request}s
 */
public interface RequestValidator {

    /**
     * Perform any validation needed on the incoming {@link Request}
     *
     * @param request incoming request to validate
     * @throws IllegalArgumentException thrown if validation fails
     */
    void validate(Request request) throws IllegalArgumentException;
}
