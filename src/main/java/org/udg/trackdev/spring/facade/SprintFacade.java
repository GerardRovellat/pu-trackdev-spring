package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.sprints.SprintRequestDTO;
import org.udg.trackdev.spring.dto.response.sprints.SprintResponseDTO;

import java.security.Principal;
import java.util.List;

/**
 * The interface Sprint facade.
 */
public interface SprintFacade {

    /**
     * Gets all sprints.
     *
     * @param principal the principal
     * @return the all sprints
     */
    List<SprintResponseDTO> getAllSprints(Principal principal);

    /**
     * Gets sprint.
     *
     * @param principal the principal
     * @param sprintId  the sprint id
     * @return the sprint
     */
    SprintResponseDTO getSprint(Principal principal, Long sprintId);

    /**
     * Edit sprint sprint response dto.
     *
     * @param request   the request
     * @param sprintId  the sprint id
     * @param principal the principal
     * @return the sprint response dto
     */
    SprintResponseDTO editSprint(SprintRequestDTO request, Long sprintId, Principal principal);

    /**
     * Delete sprint.
     *
     * @param sprintId  the sprint id
     * @param principal the principal
     */
    void deleteSprint(Long sprintId, Principal principal);



}
