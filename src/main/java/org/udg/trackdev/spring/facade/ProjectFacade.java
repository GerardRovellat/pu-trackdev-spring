package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.response.ProjectCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectWithUserResponseDTO;

import java.security.Principal;
import java.util.Collection;

public interface ProjectFacade {

    Collection<ProjectWithUserResponseDTO> getAllProjects(Principal principal);

    ProjectCompleteResponseDTO getProject(Long id, Principal principal);

}
