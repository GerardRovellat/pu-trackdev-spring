package org.udg.trackdev.spring.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Service exception.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST)  // 404
public class ServiceException extends RuntimeException {
    /**
     * Instantiates a new Service exception.
     *
     * @param message the message
     */
    public ServiceException(String message) {

        super(message);
    }
}