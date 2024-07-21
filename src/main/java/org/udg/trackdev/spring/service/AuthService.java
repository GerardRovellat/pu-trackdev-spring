package org.udg.trackdev.spring.service;

import org.udg.trackdev.spring.entity.User;

import java.security.Principal;

/**
 * The interface Auth service.
 */
public interface AuthService {

    /**
     * Gets logged in user id.
     *
     * @param principal the principal
     * @return the logged in user id
     */
    String getLoggedInUserId(Principal principal);

    /**
     * Check logged in.
     *
     * @param principal the principal
     */
    void checkLoggedIn(Principal principal);

    /**
     * Generate jwt string.
     *
     * @param user the user
     * @return the string
     */
    String generateJWT(User user);

}
