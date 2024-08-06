package org.udg.trackdev.spring.controller;

import org.springframework.http.ResponseEntity;

/**
 * Created by imartin on 21/02/17.
 */
public class BaseController {

    /**
     * Ok no content response entity.
     *
     * @return the response entity
     */
    ResponseEntity<Void> okNoContent() {
    return ResponseEntity.noContent().build();
  }

}
