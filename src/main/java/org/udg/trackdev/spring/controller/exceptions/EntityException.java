package org.udg.trackdev.spring.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Entity exception.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST)  // 400
public class EntityException extends RuntimeException {
    /**
     * Instantiates a new Entity exception.
     *
     * @param message the message
     */
    public EntityException(String message) {
        super(message);
    }
}