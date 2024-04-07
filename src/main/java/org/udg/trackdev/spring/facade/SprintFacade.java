package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.SprintRequestDTO;
import org.udg.trackdev.spring.dto.response.SprintResponseDTO;

import java.security.Principal;
import java.util.List;

public interface SprintFacade {

    List<SprintResponseDTO> getAllSprints(Principal principal);

    SprintResponseDTO getSprint(Principal principal, Long sprintId);

    SprintResponseDTO editSprint(SprintRequestDTO request, Long sprintId, Principal principal);

    void deleteSprint(Long sprintId, Principal principal);

}
