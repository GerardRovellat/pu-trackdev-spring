package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.projects.CreateSprintRequestDTO;
import org.udg.trackdev.spring.dto.request.projects.CreateTaskRequestDTO;
import org.udg.trackdev.spring.dto.request.projects.EditProjectRequestDTO;
import org.udg.trackdev.spring.dto.response.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * The interface Project facade.
 */
public interface ProjectFacade {

    /**
     * Gets all projects.
     *
     * @param principal the principal
     * @return the all projects
     */
    Collection<ProjectWithUserResponseDTO> getAllProjects(Principal principal);

    /**
     * Gets project.
     *
     * @param id        the id
     * @param principal the principal
     * @return the project
     */
    ProjectCompleteResponseDTO getProject(Long id, Principal principal);

    /**
     * Edit project project with user response dto.
     *
     * @param id        the id
     * @param request   the request
     * @param principal the principal
     * @return the project with user response dto
     */
    ProjectWithUserResponseDTO editProject(Long id, EditProjectRequestDTO request, Principal principal);

    /**
     * Delete project.
     *
     * @param id        the id
     * @param principal the principal
     */
    void deleteProject(Long id, Principal principal);

    /**
     * Gets project tasks.
     *
     * @param id        the id
     * @param principal the principal
     * @return the project tasks
     */
    ProjectTaskResponseDTO getProjectTasks(Long id, Principal principal);

    /**
     * Create project sprint.
     *
     * @param request   the request
     * @param projectId the project id
     * @param principal the principal
     */
    void createProjectSprint(CreateSprintRequestDTO request, Long projectId, Principal principal);

    /**
     * Create project task project response dto.
     *
     * @param request   the request
     * @param projectId the project id
     * @param principal the principal
     * @return the project response dto
     */
    ProjectResponseDTO createProjectTask(CreateTaskRequestDTO request, Long projectId, Principal principal);

    /**
     * Gets project sprints.
     *
     * @param projectId the project id
     * @param principal the principal
     * @return the project sprints
     */
    List<ProjectSprintsResponseDTO> getProjectSprints(Long projectId, Principal principal);

    /**
     * Gets project rak.
     *
     * @param projectId the project id
     * @param principal the principal
     * @return the project rak
     */
    Map<String, ProjectRankDTO> getProjectRak(Long projectId, Principal principal);

}
