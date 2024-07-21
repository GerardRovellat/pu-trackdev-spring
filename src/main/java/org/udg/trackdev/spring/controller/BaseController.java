package org.udg.trackdev.spring.controller;

import org.springframework.http.ResponseEntity;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.security.Principal;

/**
 * Created by imartin on 21/02/17.
 */
public class BaseController {

    /**
     * Gets user id.
     *
     * @param principal the principal
     * @return the user id
     */
    protected String getUserId(Principal principal) {
    checkLoggedIn(principal);
    return principal.getName();
  }

    /**
     * Check logged in.
     *
     * @param principal the principal
     */
    void checkLoggedIn(Principal principal) {
    if(principal == null)
      throw new ControllerException(ErrorConstants.USER_NOT_LOGGED_IN);
  }

    /**
     * Ok no content response entity.
     *
     * @return the response entity
     */
    ResponseEntity<Void> okNoContent() {
    return ResponseEntity.noContent().build();
  }

}
