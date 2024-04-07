package org.udg.trackdev.spring.service;

import org.udg.trackdev.spring.entity.User;

import java.security.Principal;

public interface AuthService {

    String getLoggedInUserId(Principal principal);

    void checkLoggedIn(Principal principal);

    String generateJWT(User user);

}
