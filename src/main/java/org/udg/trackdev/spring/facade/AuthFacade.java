package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.ChangePasswordRequestDTO;
import org.udg.trackdev.spring.dto.request.LoginRequestDTO;
import org.udg.trackdev.spring.dto.request.RecoveryPasswordRequestDTO;
import org.udg.trackdev.spring.dto.response.LoginResponseDTO;
import org.udg.trackdev.spring.entity.User;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

public interface AuthFacade {

    LoginResponseDTO login(HttpServletRequest httpRequest, HttpServletResponse httpResponse, LoginRequestDTO request);

    void logout(HttpServletRequest httpRequest, HttpServletResponse httpResponse);

    User self(Principal principal);

    void check(Principal principal);

    void changePassword(ChangePasswordRequestDTO request, Principal principal);

    void recoveryCode(String email) throws MessagingException;

    void checkRecoveryCode(String email, String code);

    void recoveryPassword(String email, RecoveryPasswordRequestDTO request);

}
