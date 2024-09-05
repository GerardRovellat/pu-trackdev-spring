package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.users.EditUserRequestDTO;
import org.udg.trackdev.spring.dto.request.users.RegisterUserRequestDTO;
import org.udg.trackdev.spring.dto.response.UserWithoutProjectMembersResponseDTO;

import java.security.Principal;
import java.util.List;

/**
 * The interface User facade.
 */
public interface UserFacade {

    /**
     * Gets user.
     *
     * @param id        the id
     * @param principal the principal
     * @return the user
     */
    UserWithoutProjectMembersResponseDTO getUser(String id, Principal principal);

    /**
     * Gets user email.
     *
     * @param email     the email
     * @param principal the principal
     * @return the user email
     */
    UserWithoutProjectMembersResponseDTO getUserEmail(String email, Principal principal);

    /**
     * Gets all users.
     *
     * @param principal the principal
     * @return the all users
     */
    List<UserWithoutProjectMembersResponseDTO> getAllUsers(Principal principal);

    /**
     * Register user.
     *
     * @param request   the request
     * @param principal the principal
     */
    void registerUser(RegisterUserRequestDTO request, Principal principal);

    /**
     * Edit my user user without project members response dto.
     *
     * @param request   the request
     * @param principal the principal
     * @return the user without project members response dto
     */
    UserWithoutProjectMembersResponseDTO editMyUser(EditUserRequestDTO request, Principal principal);

    /**
     * Edit other user user without project members response dto.
     *
     * @param request   the request
     * @param principal the principal
     * @param id        the id
     * @return the user without project members response dto
     */
    UserWithoutProjectMembersResponseDTO editOtherUser(EditUserRequestDTO request, Principal principal, String id);

    /**
     * Im admin user boolean.
     *
     * @param principal the principal
     * @return the boolean
     */
    boolean imAdminUser(Principal principal);

}
