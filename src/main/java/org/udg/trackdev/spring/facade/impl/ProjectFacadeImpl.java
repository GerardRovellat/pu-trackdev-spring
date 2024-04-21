package org.udg.trackdev.spring.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.dto.response.ProjectCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectWithUserResponseDTO;
import org.udg.trackdev.spring.entity.Project;
import org.udg.trackdev.spring.facade.ProjectFacade;
import org.udg.trackdev.spring.mappers.EntityMapper;
import org.udg.trackdev.spring.service.AccessChecker;
import org.udg.trackdev.spring.service.AuthService;
import org.udg.trackdev.spring.service.ProjectService;
import org.udg.trackdev.spring.service.UserService;

import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;

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
}
