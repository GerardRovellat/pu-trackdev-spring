package org.udg.trackdev.spring.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Security exception.
 */
@ResponseStatus(value= HttpStatus.FORBIDDEN)
public class SecurityException extends RuntimeException {
    /**
     * Instantiates a new Security exception.
     *
     * @param message the message
     */
    public SecurityException(String message) {
        super(message);
    }
}