package org.udg.trackdev.spring.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.dto.request.sprints.SprintRequestDTO;
import org.udg.trackdev.spring.dto.response.SprintResponseDTO;
import org.udg.trackdev.spring.facade.SprintFacade;
import org.udg.trackdev.spring.mappers.EntityMapper;
import org.udg.trackdev.spring.service.AccessChecker;
import org.udg.trackdev.spring.service.AuthService;
import org.udg.trackdev.spring.service.SprintService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Sprint facade.
 */
@Component
@RequiredArgsConstructor
public class SprintFacadeImpl implements SprintFacade {

    private final SprintService sprintService;

    private final AuthService authService;

    private final AccessChecker accessChecker;

    private final EntityMapper mapper;

    @Override
    public List<SprintResponseDTO> getAllSprints(Principal principal) {
        accessChecker.checkCanViewAllProjects(authService.getLoggedInUserId(principal));
        return sprintService.getAll().stream().map(mapper::sprintEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public SprintResponseDTO getSprint(Principal principal, Long sprintId) {
        accessChecker.checkCanViewProject(sprintService.get(sprintId).getProject(),authService.getLoggedInUserId(principal));
        return mapper.sprintEntityToDTO(sprintService.get(sprintId));
    }

    @Override
    public SprintResponseDTO editSprint(SprintRequestDTO request, Long sprintId, Principal principal) {
        String userId = authService.getLoggedInUserId(principal);
        accessChecker.checkCanViewProject(sprintService.get(sprintId).getProject(),userId);
        return mapper.sprintEntityToDTO(sprintService.editSprint(sprintId, request, userId));
    }

    @Override
    public void deleteSprint(Long sprintId, Principal principal) {
        accessChecker.checkCanViewProject(sprintService.get(sprintId).getProject(),authService.getLoggedInUserId(principal));
        sprintService.deleteSprint(sprintId);
    }
}
