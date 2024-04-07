package org.udg.trackdev.spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.udg.trackdev.spring.dto.request.ChangePasswordRequestDTO;
import org.udg.trackdev.spring.dto.request.LoginRequestDTO;
import org.udg.trackdev.spring.dto.request.RecoveryPasswordRequestDTO;
import org.udg.trackdev.spring.dto.response.LoginResponseDTO;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;
import org.udg.trackdev.spring.entity.views.PrivacyLevelViews;
import org.udg.trackdev.spring.facade.AuthFacade;
import org.udg.trackdev.spring.utils.ValidatorHelper;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@Tag(name = "1. Authentication")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController extends BaseController {

    private final AuthFacade facade;

    private final ValidatorHelper validatorHelper = new ValidatorHelper();

    @Operation(summary = "Login user", description = "Login user with username and password")
    @PostMapping(path="/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request, HttpServletRequest httpRequest,
                                  HttpServletResponse httpResponse){

        return facade.login(httpRequest, httpResponse, request);
    }

    @Operation(summary = "Logout user",
            description = "Logout user",
            security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping(path="/logout")
    @JsonView(PrivacyLevelViews.Private.class)
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        facade.logout(request, response);
        return okNoContent();
    }

    @Operation(summary = "Return the logged user",
            description = "Return the public information of the logged user",
            security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping(path="/self")
    @JsonView({EntityLevelViews.UserWithGithubToken.class})
    public User self(Principal principal) {
        return facade.self(principal);
    }

    @Operation(summary = "Check if user is logged",
            description = "Check if the user is logged to the website",
            security = {@SecurityRequirement(name = "bearerAuth")})
    @GetMapping(path = "/check")
    public ResponseEntity<Void> check(Principal principal) {
        facade.check(principal);
        return okNoContent();
    }

    @Operation(summary = "Change user password",
            description = "Change the password of the user for a new one",
            security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping(path="/password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequestDTO request, Principal principal,
                                               BindingResult validation) {
        validatorHelper.validateRequest(validation);
        facade.changePassword(request, principal);
        return okNoContent();
    }

    @Operation(summary = "Get recovery code", description = "Get recovery code for user")
    @PostMapping(path="/recovery")
    public ResponseEntity<Void> recoveryCode(@RequestParam String email)
            throws MessagingException {
        facade.recoveryCode(email);
        return okNoContent();
    }

    @Operation(summary = "Check recovery code", description = "Check recovery code for user")
    @PostMapping(path="/recovery/{email}/check")
    public ResponseEntity<Void> checkRecoveryCode(@PathVariable("email") String email, @RequestParam String code) {
        facade.checkRecoveryCode(email,code);
        return okNoContent();
    }

    @Operation(summary = "Recovery password", description = "Recover password with recovery code")
    @PostMapping(path="/recovery/{email}")
    public ResponseEntity<Void> recoveryPassword(@Valid @RequestBody RecoveryPasswordRequestDTO request,
                                                 @PathVariable("email") String email, BindingResult validation) {
        validatorHelper.validateRequest(validation);
        facade.recoveryPassword(email,request);
        return okNoContent();
    }

}
