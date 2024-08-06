package org.udg.trackdev.spring.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.request.users.EditUserRequestDTO;
import org.udg.trackdev.spring.dto.request.users.RegisterUserRequestDTO;
import org.udg.trackdev.spring.dto.response.users.UserDTO;
import org.udg.trackdev.spring.dto.response.users.UserWithoutProjectMembersResponseDTO;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.facade.UserFacade;
import org.udg.trackdev.spring.mappers.EntityMapper;
import org.udg.trackdev.spring.service.AccessChecker;
import org.udg.trackdev.spring.service.AuthService;
import org.udg.trackdev.spring.service.UserService;
import org.udg.trackdev.spring.utils.Constants;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type User facade.
 */
@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final AuthService authService;

    private final UserService userService;

    private final AccessChecker accessChecker;

    private final EntityMapper mapper;

    @Override
    public UserWithoutProjectMembersResponseDTO getUser(String id, Principal principal) {
        authService.checkLoggedIn(principal);
        return mapper.userEntityToUserWithoutProjectMembers(userService.get(id));
    }

    @Override
    public UserWithoutProjectMembersResponseDTO getUserEmail(String email, Principal principal) {
        authService.checkLoggedIn(principal);
        return mapper.userEntityToUserWithoutProjectMembers(userService.getByEmail(email));
    }

    @Override
    public List<UserWithoutProjectMembersResponseDTO> getAllUsers(Principal principal) {
        if (!accessChecker.isUserAdmin(userService.get(principal.getName()))){
            throw new SecurityException(ErrorConstants.EMPTY);
        }
        return userService.getAll().stream().map(mapper::userEntityToUserWithoutProjectMembers)
                .collect(Collectors.toList());
    }

    @Override
    public void registerUser(RegisterUserRequestDTO request, Principal principal) {
        authService.checkLoggedIn(principal);
        if (!accessChecker.isUserAdmin(userService.get(principal.getName()))) {
            throw new SecurityException(ErrorConstants.UNAUTHORIZED);
        }
        userService.register(request.getUsername(), request.getEmail());
    }

    @Override
    public UserWithoutProjectMembersResponseDTO editMyUser(EditUserRequestDTO request, Principal principal) {
        if (request.getUsername() != null){
            if (request.getUsername().isEmpty() || request.getUsername().length() > Constants.USERNAME_LENGTH) {
                throw new ControllerException(ErrorConstants.INVALID_USERNAME_SIZE);
            }
            if (!request.getUsername().matches("^[a-zA-ZÀ-ÖØ-öø-ÿ ]+$")) {
                throw new ControllerException(ErrorConstants.INVALID_USERNAME_FORMAT);
            }
        }
        String userId = authService.getLoggedInUserId(principal);
        User user = userService.get(userId);
        return mapper.userEntityToUserWithoutProjectMembers(userService.editMyUser(user, user, request.getUsername(), request.getColor(),
                request.getCapitalLetters(), request.getChangePassword(),
                request.getGithubToken(), request.getEnabled()));
    }

    @Override
    public UserWithoutProjectMembersResponseDTO editOtherUser(EditUserRequestDTO request, Principal principal, String id) {
        if (request.getUsername() != null){
            if (request.getUsername().isEmpty() || request.getUsername().length() > User.USERNAME_LENGTH) {
                throw new ControllerException(ErrorConstants.INVALID_USERNAME_SIZE);
            }
            if (!request.getUsername().matches("^[a-zA-ZÀ-ÖØ-öø-ÿ ]+$")) {
                throw new ControllerException(ErrorConstants.INVALID_USERNAME_FORMAT);
            }
        }
        User modifier = userService.get(authService.getLoggedInUserId(principal));
        if (!accessChecker.isUserAdmin(modifier)){
            throw new SecurityException(ErrorConstants.UNAUTHORIZED);
        }
        else {
            User user = userService.get(id);
            return mapper.userEntityToUserWithoutProjectMembers(userService.editMyUser(modifier, user, request.getUsername(), request.getColor(),
                    request.getCapitalLetters(), request.getChangePassword(), request.getGithubToken(), request.getEnabled()));
        }
    }

    @Override
    public boolean imAdminUser(Principal principal) {
        User user = userService.get(authService.getLoggedInUserId(principal));
        if (accessChecker.isUserAdmin(user)) {
            return true;
        } else {
            throw new SecurityException(ErrorConstants.EMPTY);
        }
    }


}
