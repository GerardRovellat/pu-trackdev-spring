package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.CreateSprintRequestDTO;
import org.udg.trackdev.spring.dto.request.CreateTaskRequestDTO;
import org.udg.trackdev.spring.dto.request.EditProjectRequestDTO;
import org.udg.trackdev.spring.dto.response.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ProjectFacade {

    Collection<ProjectWithUserResponseDTO> getAllProjects(Principal principal);

    ProjectCompleteResponseDTO getProject(Long id, Principal principal);

    ProjectWithUserResponseDTO editProject(Long id, EditProjectRequestDTO request, Principal principal);

    void deleteProject(Long id, Principal principal);

    ProjectTaskResponseDTO getProjectTasks(Long id, Principal principal);

    void createProjectSprint(CreateSprintRequestDTO request, Long projectId, Principal principal);

    ProjectResponseDTO createProjectTask(CreateTaskRequestDTO request, Long projectId, Principal principal);

    List<ProjectSprintsResponseDTO> getProjectSprints(Long projectId, Principal principal);

    Map<String, ProjectRankDTO> getProjectRak(Long projectId, Principal principal);

}
