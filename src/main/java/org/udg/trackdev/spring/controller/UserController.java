package org.udg.trackdev.spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.entity.views.PrivacyLevelViews;
import org.udg.trackdev.spring.service.AccessChecker;
import org.udg.trackdev.spring.service.UserService;


import javax.validation.Valid;
import javax.validation.constraints.*;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

// This class is used to manage users and sign up
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "2. Users")
@RequestMapping(path = "/users")
@RestController
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    AccessChecker accessChecker;

    /**
     * Returns the public profile of any user.
     * @param principal The current authenticated entity
     * @param email The email of the user to request.
     * @return The User identified by username
     */
    @GetMapping(path = "/{email}")
    @JsonView(PrivacyLevelViews.Public.class)
    public User getPublic(Principal principal, @PathVariable("email") String email) {
        super.checkLoggedIn(principal);
        return userService.getByEmail(email);
    }

    @GetMapping
    public List<User> getAll(Principal principal) {
        if (!accessChecker.isUserAdminOrProfessor(userService.get(principal.getName()))){
            throw new SecurityException("Only admins can list all users");
        }
        return userService.findAll();
    }

    @PostMapping(path = "/register")
    public ResponseEntity register(Principal principal, @Valid @RequestBody RegisterU ru) {
        checkLoggedIn(principal);
        if (!accessChecker.isUserAdminOrProfessor(userService.get(principal.getName()))) {
            throw new SecurityException("Only admins can register users");
        }
        userService.register(ru.username, ru.email);
        return okNoContent();
    }

    @PatchMapping
    public User editMyUser(Principal principal,
                         @Valid @RequestBody EditU userRequest) {
        String userId = super.getUserId(principal);
        User user = userService.get(userId);
        User modifiedUser = userService.editMyUser(user, userRequest.username, userRequest.color,
                userRequest.capitalLetters, userRequest.changePassword, userRequest.githubToken);
        return modifiedUser;
    }

    @GetMapping(path = "/checker/admin")
    public ResponseEntity<Void> imAdminUser(Principal principal) {
        String userId = super.getUserId(principal);
        User user = userService.get(userId);
        if (accessChecker.isUserAdminOrProfessor(user)) {
            return okNoContent();
        } else {
            throw new SecurityException("You are not admin");
        }
    }

    static class RegisterU {
        @NotBlank
        @Size(min = 4, max = User.USERNAME_LENGTH)
        @Pattern(regexp = "[a-zA-Z0-9]+")
        public String username;

        @NotBlank
        @Email
        @Size(max = User.EMAIL_LENGTH)
        public String email;

    }

    static class EditU {

        public Optional<String> username;

        public Optional<String> color;

        public Optional<String> capitalLetters;

        public Optional<Boolean> changePassword;

        public Optional<String> githubToken;
    }

}
