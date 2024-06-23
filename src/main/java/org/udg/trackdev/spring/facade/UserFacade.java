package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.UserDTO;
import org.udg.trackdev.spring.dto.request.EditUserRequestDTO;
import org.udg.trackdev.spring.dto.request.RegisterUserRequestDTO;
import org.udg.trackdev.spring.dto.response.UserWithoutProjectMembersResponseDTO;

import java.security.Principal;
import java.util.List;

public interface UserFacade {

    UserDTO getUser(String id, Principal principal);

    UserWithoutProjectMembersResponseDTO getUserEmail(String email, Principal principal);

    List<UserWithoutProjectMembersResponseDTO> getAllUsers(Principal principal);

    void registerUser(RegisterUserRequestDTO request, Principal principal);

    UserWithoutProjectMembersResponseDTO editMyUser(EditUserRequestDTO request, Principal principal);

}
