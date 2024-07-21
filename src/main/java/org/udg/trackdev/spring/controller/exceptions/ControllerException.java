package org.udg.trackdev.spring.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Controller exception.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST)  // 400
public class ControllerException extends RuntimeException {
    /**
     * Instantiates a new Controller exception.
     *
     * @param message the message
     */
    public ControllerException(String message) {
        super(message);
    }
}