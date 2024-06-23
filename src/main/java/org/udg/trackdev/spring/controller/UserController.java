package org.udg.trackdev.spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.UserDTO;
import org.udg.trackdev.spring.dto.request.EditUserRequestDTO;
import org.udg.trackdev.spring.dto.request.RegisterUserRequestDTO;
import org.udg.trackdev.spring.dto.response.UserWithoutProjectMembersResponseDTO;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;
import org.udg.trackdev.spring.facade.UserFacade;
import org.udg.trackdev.spring.service.AccessChecker;
import org.udg.trackdev.spring.service.UserService;
import org.udg.trackdev.spring.utils.Constants;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// This class is used to manage users and sign up
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "2. Users")
@RequestMapping(path = "/users")
@RestController
public class UserController extends BaseController {

    @Autowired
    private UserFacade facade;

    @Autowired
    UserService userService;

    @Autowired
    AccessChecker accessChecker;

    /**
     * Returns the public profile of any user.
     * @param principal The current authenticated entity
     * @param id The email of the user to request.
     * @return The User identified by username
     */
    @Operation(summary = "Get user by id", description = "Get user by id")
    @GetMapping(path = "/uuid/{id}")
    public UserDTO getPublic(@PathVariable("id") String id, Principal principal) {
        return facade.getUser(id, principal);
    }

    /**
     * Returns the public profile of any user.
     * @param principal The current authenticated entity
     * @param email The email of the user to request.
     * @return The User identified by username
     */
    @Operation(summary = "Get user by email", description = "Get user by email")
    @GetMapping(path = "/{email}")
    public UserWithoutProjectMembersResponseDTO getUserEmail(@PathVariable("email") String email, Principal principal) {
        return facade.getUserEmail(email, principal);
    }

    @Operation(summary = "Get all users", description = "Get all users, only admins can do this")
    @GetMapping
    public List<UserWithoutProjectMembersResponseDTO> getAllUsers(Principal principal) {
        return facade.getAllUsers(principal);
    }

    @Operation(summary = "Register user", description = "Register user, only admins can do this")
    @PostMapping(path = "/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterUserRequestDTO request, Principal principal,
                                         BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            throw new ControllerException(String.join(". ", errors));
        }
        facade.registerUser(request,principal);
        return okNoContent();
    }

    @Operation(summary = "Edit your user", description = "Edit your user")
    @PatchMapping
    public UserWithoutProjectMembersResponseDTO editMyUser(@Valid @RequestBody EditUserRequestDTO request, Principal principal) {
        return facade.editMyUser(request, principal);
    }

    @Operation(summary = "Edit user", description = "Edit user, only admins can do this")
    @PatchMapping(path = "/{id}")
    @JsonView({EntityLevelViews.UserWithoutProjectMembers.class})
    public User editUser(@Valid @RequestBody EditUserRequestDTO request, @PathVariable("id") String id,
                         Principal principal) {
        if (request.username != null){
            if (userRequest.username.get().isEmpty() || userRequest.username.get().length() > User.USERNAME_LENGTH) {
                throw new ControllerException(ErrorConstants.INVALID_USERNAME_SIZE);
            }
            if (!userRequest.username.get().matches("^[a-zA-ZÀ-ÖØ-öø-ÿ ]+$")) {
                throw new ControllerException(ErrorConstants.INVALID_USERNAME_FORMAT);
            }
        }
        String userId = super.getUserId(principal);
        User modifier = userService.get(userId);
        if (!accessChecker.isUserAdmin(modifier)){
            throw new SecurityException(ErrorConstants.UNAUTHORIZED);
        }
        else {
            User user = userService.get(id);
            return userService.editMyUser(modifier, user, userRequest.username, userRequest.color,
                    userRequest.capitalLetters, userRequest.changePassword, userRequest.githubToken, userRequest.enabled);
        }
        return facade.editOtherUser(request, principal);
    }

    @Operation(summary = "Check if autenticated user is admin", description = "Check if autenticated user is admin")
    @GetMapping(path = "/checker/admin")
    public ResponseEntity<Void> imAdminUser(Principal principal) {
        String userId = super.getUserId(principal);
        User user = userService.get(userId);
        if (accessChecker.isUserAdmin(user)) {
            return okNoContent();
        } else {
            throw new SecurityException(ErrorConstants.EMPTY);
        }
    }

}
