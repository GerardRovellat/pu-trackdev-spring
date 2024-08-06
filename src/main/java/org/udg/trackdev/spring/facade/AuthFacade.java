package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.auth.ChangePasswordRequestDTO;
import org.udg.trackdev.spring.dto.request.auth.LoginRequestDTO;
import org.udg.trackdev.spring.dto.request.auth.RecoveryPasswordRequestDTO;
import org.udg.trackdev.spring.dto.response.auth.LoginResponseDTO;
import org.udg.trackdev.spring.dto.response.auth.SelfResponseDTO;
import org.udg.trackdev.spring.dto.response.users.UserDTO;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * The interface Auth facade.
 */
public interface AuthFacade {

    /**
     * Login login response dto.
     *
     * @param httpRequest  the http request
     * @param httpResponse the http response
     * @param request      the request
     * @return the login response dto
     */
    LoginResponseDTO login(HttpServletRequest httpRequest, HttpServletResponse httpResponse, LoginRequestDTO request);

    /**
     * Logout.
     *
     * @param httpRequest  the http request
     * @param httpResponse the http response
     */
    void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse);

    /**
     * Self user.
     *
     * @param principal the principal
     * @return the user
     */
    SelfResponseDTO self(Principal principal);

    /**
     * Check.
     *
     * @param principal the principal
     */
    void check(Principal principal);

    /**
     * Change password.
     *
     * @param request   the request
     * @param principal the principal
     */
    void changePassword(ChangePasswordRequestDTO request, Principal principal);

    /**
     * Recovery code.
     *
     * @param email the email
     * @throws MessagingException the messaging exception
     */
    void recoveryCode(String email) throws MessagingException;

    /**
     * Check recovery code.
     *
     * @param email the email
     * @param code  the code
     */
    void checkRecoveryCode(String email, String code);

    /**
     * Recovery password.
     *
     * @param email   the email
     * @param request the request
     */
    void recoveryPassword(String email, RecoveryPasswordRequestDTO request);

}
