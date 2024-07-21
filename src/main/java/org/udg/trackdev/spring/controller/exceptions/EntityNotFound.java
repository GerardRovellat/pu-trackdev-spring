package org.udg.trackdev.spring.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Entity not found.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such entity")  // 404
public class EntityNotFound extends RuntimeException {
    /**
     * Instantiates a new Entity not found.
     */
    public EntityNotFound() {
        super("No such entity");
    }

    /**
     * Instantiates a new Entity not found.
     *
     * @param message the message
     */
    public EntityNotFound(String message) {
        super(message);
    }
}