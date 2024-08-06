package org.udg.trackdev.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.request.users.EditUserRequestDTO;
import org.udg.trackdev.spring.dto.request.users.RegisterUserRequestDTO;
import org.udg.trackdev.spring.dto.response.users.UserDTO;
import org.udg.trackdev.spring.dto.response.users.UserWithoutProjectMembersResponseDTO;
import org.udg.trackdev.spring.facade.UserFacade;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type User controller.
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "2. Users")
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@RestController
public class UserController extends BaseController {

    private final UserFacade facade;

    /**
     * Returns the public profile of any user.
     *
     * @param id        The email of the user to request.
     * @param principal The current authenticated entity
     * @return The User identified by username
     */
    @Operation(summary = "Get user by id", description = "Get user by id")
    @GetMapping(path = "/uuid/{id}")
    public UserWithoutProjectMembersResponseDTO getPublic(@PathVariable("id") String id, Principal principal) {
        return facade.getUser(id, principal);
    }

    /**
     * Returns the public profile of any user.
     *
     * @param email     The email of the user to request.
     * @param principal The current authenticated entity
     * @return The User identified by username
     */
    @Operation(summary = "Get user by email", description = "Get user by email")
    @GetMapping(path = "/{email}")
    public UserWithoutProjectMembersResponseDTO getUserEmail(@PathVariable("email") String email, Principal principal) {
        return facade.getUserEmail(email, principal);
    }

    /**
     * Gets all users.
     *
     * @param principal the principal
     * @return the all users
     */
    @Operation(summary = "Get all users", description = "Get all users, only admins can do this")
    @GetMapping
    public List<UserWithoutProjectMembersResponseDTO> getAllUsers(Principal principal) {
        return facade.getAllUsers(principal);
    }

    /**
     * Register response entity.
     *
     * @param request   the request
     * @param principal the principal
     * @param result    the result
     * @return the response entity
     */
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

    /**
     * Edit my user without project members response dto.
     *
     * @param request   the request
     * @param principal the principal
     * @return the user without project members response dto
     */
    @Operation(summary = "Edit your user", description = "Edit your user")
    @PatchMapping
    public UserWithoutProjectMembersResponseDTO editMyUser(@Valid @RequestBody EditUserRequestDTO request, Principal principal) {
        return facade.editMyUser(request, principal);
    }

    /**
     * Edit user without project members response dto.
     *
     * @param request   the request
     * @param id        the id
     * @param principal the principal
     * @return the user without project members response dto
     */
    @Operation(summary = "Edit user", description = "Edit user, only admins can do this")
    @PatchMapping(path = "/{id}")
    public UserWithoutProjectMembersResponseDTO editUser(@Valid @RequestBody EditUserRequestDTO request, @PathVariable("id") String id,
                         Principal principal) {
        return facade.editOtherUser(request, principal, id);
    }

    /**
     * Im admin user response entity.
     *
     * @param principal the principal
     * @return the response entity
     */
    @Operation(summary = "Check if autenticated user is admin", description = "Check if autenticated user is admin")
    @GetMapping(path = "/checker/admin")
    public ResponseEntity<Void> imAdminUser(Principal principal) {
        if (facade.imAdminUser(principal)) {
            return okNoContent();
        } else {
            throw new SecurityException(ErrorConstants.EMPTY);
        }
    }

}
