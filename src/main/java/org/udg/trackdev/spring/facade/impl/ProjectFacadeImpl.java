package org.udg.trackdev.spring.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.dto.request.projects.CreateSprintRequestDTO;
import org.udg.trackdev.spring.dto.request.projects.CreateTaskRequestDTO;
import org.udg.trackdev.spring.dto.request.projects.EditProjectRequestDTO;
import org.udg.trackdev.spring.dto.response.*;
import org.udg.trackdev.spring.dto.response.TaskResponseDTO;
import org.udg.trackdev.spring.entity.Project;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.facade.ProjectFacade;
import org.udg.trackdev.spring.mappers.EntityMapper;
import org.udg.trackdev.spring.service.AccessChecker;
import org.udg.trackdev.spring.service.AuthService;
import org.udg.trackdev.spring.service.ProjectService;
import org.udg.trackdev.spring.service.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Project facade.
 */
@Component
@RequiredArgsConstructor
public class ProjectFacadeImpl implements ProjectFacade {

    private final AuthService authService;

    private final AccessChecker accessChecker;

    private final UserService userService;

    private final ProjectService projectService;

    private final EntityMapper mapper;

    @Override
    public Collection<ProjectWithUserResponseDTO> getAllProjects(Principal principal) {
        return accessChecker.checkCanViewAllProjects(authService.getLoggedInUserId(principal))
                ? projectService.getAll().stream().map(mapper::projectEntityToProjectWithUserDTO).collect(Collectors.toList())
                : userService.get(authService.getLoggedInUserId(principal)).getProjects().stream().map(mapper::projectEntityToProjectWithUserDTO).collect(Collectors.toList());
    }

    @Override
    public ProjectCompleteResponseDTO getProject(Long id, Principal principal) {
        String userId = authService.getLoggedInUserId(principal);
        Project project = projectService.get(id);
        accessChecker.checkCanViewProject(project, userId);
        userService.setCurrentProject(userService.get(userId), project);
        return mapper.projectEntityToProjectCompleteDTO(project);
    }

    @Override
    public ProjectWithUserResponseDTO editProject(Long id, EditProjectRequestDTO request, Principal principal) {
        String userId = authService.getLoggedInUserId(principal);
        return mapper.projectEntityToProjectWithUserDTO(
                projectService.editProject(id, request.getName(), request.getMembers(), request.getCourseId(), request.getQualification(), userId)
        );
    }

    @Override
    public void deleteProject(Long id, Principal principal) {
        projectService.deleteProject(id, authService.getLoggedInUserId(principal));
    }

    @Override
    public ProjectTaskResponseDTO getProjectTasks(Long id, Principal principal) {
        String userId = authService.getLoggedInUserId(principal);
        Project project = projectService.get(id);
        accessChecker.checkCanViewProject(project, userId);
        Collection<TaskResponseDTO> tasks = projectService.getProjectTasks(project).stream().map(mapper::taskEntityToDTO).collect(Collectors.toList());
        return ProjectTaskResponseDTO.builder()
                .tasks(tasks)
                .projectId(id)
                .build();
    }

    @Override
    public void createProjectSprint(CreateSprintRequestDTO request, Long projectId, Principal principal) {
        String userId = authService.getLoggedInUserId(principal);
        Project project = projectService.get(projectId);
        accessChecker.checkCanViewProject(project, userId);
        projectService.createSprint(project, request.getName(), request.getStartDate(), request.getEndDate(), userId);
    }

    @Override
    public ProjectResponseDTO createProjectTask(CreateTaskRequestDTO request, Long projectId, Principal principal) {
        String userId = authService.getLoggedInUserId(principal);
        User user = userService.get(userId);
        Project project = projectService.get(projectId);
        accessChecker.checkCanViewProject(project, userId);
        return mapper.projectEntityToDTO(projectService.createProjectTask(project,request.getName(), user));
    }

    @Override
    public List<ProjectSprintsResponseDTO> getProjectSprints(Long projectId, Principal principal) {
        String userId = authService.getLoggedInUserId(principal);
        Project project = projectService.get(projectId);
        accessChecker.checkCanViewProject(project, userId);
        return projectService.getProjectSprints(project).stream().map(mapper::sprintEntityToProjectSprintsDTO)
                .collect(Collectors.toList());

    }

    @Override
    public Map<String, ProjectRankDTO> getProjectRak(Long projectId, Principal principal) {
        User user = userService.get(authService.getLoggedInUserId(principal));
        Project project = projectService.get(projectId);
        accessChecker.isUserAdmin(user);
        return projectService.getProjectRanks(project);
    }
}
