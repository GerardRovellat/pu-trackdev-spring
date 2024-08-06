package org.udg.trackdev.spring.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.configuration.CookieManager;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.request.auth.ChangePasswordRequestDTO;
import org.udg.trackdev.spring.dto.request.auth.LoginRequestDTO;
import org.udg.trackdev.spring.dto.request.auth.RecoveryPasswordRequestDTO;
import org.udg.trackdev.spring.dto.response.auth.LoginResponseDTO;
import org.udg.trackdev.spring.dto.response.auth.SelfResponseDTO;
import org.udg.trackdev.spring.dto.response.users.UserDTO;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.facade.AuthFacade;
import org.udg.trackdev.spring.mappers.EntityMapper;
import org.udg.trackdev.spring.service.AuthService;
import org.udg.trackdev.spring.service.EmailSenderService;
import org.udg.trackdev.spring.service.UserService;
import org.udg.trackdev.spring.utils.Constants;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Base64;

/**
 * The type Auth facade.
 */
@Component
@RequiredArgsConstructor
public class AuthFacadeImpl implements AuthFacade {

    private final AuthService authService;

    private final UserService userService;

    private final CookieManager cookieManager;

    private final EmailSenderService emailService;

    private final EntityMapper mapper;

    @Override
    public LoginResponseDTO login(HttpServletRequest httpRequest, HttpServletResponse httpResponse, LoginRequestDTO request) {
        User loggedInUser = userService.matchPassword(request.getEmail(), request.getPassword());
        String token = authService.generateJWT(loggedInUser);
        String cookieTokenValue = Base64.getEncoder().withoutPadding().encodeToString(token.getBytes());
        cookieManager.addSessionCookie(httpRequest, httpResponse, Constants.COOKIE_NAME, cookieTokenValue);
        userService.setLastLogin(loggedInUser);
        return LoginResponseDTO.builder().userdata(mapper.userEntityToUserLoginDTO(loggedInUser)).token(token).build();
    }

    @Override
    public void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        cookieManager.removeCookie(httpRequest, httpResponse, Constants.COOKIE_NAME);
    }

    @Override
    public SelfResponseDTO self(Principal principal) {
        return mapper.userEntityToSelfDTO(userService.get(authService.getLoggedInUserId(principal)));
    }

    @Override
    public void check(Principal principal) {
        authService.checkLoggedIn(principal);
    }

    @Override
    public void changePassword(ChangePasswordRequestDTO request, Principal principal) {
        User loggedInUser = userService.get(authService.getLoggedInUserId(principal));
        userService.matchPassword(loggedInUser.getEmail(), request.oldPassword);
        userService.changePassword(loggedInUser, request.newPassword);
    }

    @Override
    public void recoveryCode(String email) throws MessagingException {
        User user = userService.getByEmail(email);
        if(null == user) throw new ControllerException(ErrorConstants.USER_MAIL_NOT_FOUND);
        emailService.sendRecoveryEmail(email,userService.generateRecoveryCode(user));
    }

    @Override
    public void checkRecoveryCode(String email, String code) {
        User user = userService.getByEmail(email);
        if(user == null) {
            throw new ControllerException(ErrorConstants.USER_MAIL_NOT_FOUND);
        }
        userService.matchRecoveryCode(user, code);
    }

    @Override
    public void recoveryPassword(String email, RecoveryPasswordRequestDTO request) {
        User user = userService.getByEmail(email);
        if(user == null) {
            throw new ControllerException(ErrorConstants.USER_MAIL_NOT_FOUND);
        }
        userService.matchRecoveryCode(user, request.code);
        userService.changePassword(user, request.newPassword);
    }

}
