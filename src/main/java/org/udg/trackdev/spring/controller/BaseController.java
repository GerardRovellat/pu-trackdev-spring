package org.udg.trackdev.spring.controller;

import org.springframework.http.ResponseEntity;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.security.Principal;

/**
 * Created by imartin on 21/02/17.
 */
public class BaseController {

  protected String getUserId(Principal principal) {
    checkLoggedIn(principal);
    return principal.getName();
  }

  void checkLoggedIn(Principal principal) {
    if(principal == null)
      throw new ControllerException(ErrorConstants.USER_NOT_LOGGED_IN);
  }

  ResponseEntity<Void> okNoContent() {
    return ResponseEntity.noContent().build();
  }

}
