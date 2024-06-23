package org.udg.trackdev.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.request.CreateSprintRequestDTO;
import org.udg.trackdev.spring.dto.request.CreateTaskRequestDTO;
import org.udg.trackdev.spring.dto.request.EditProjectRequestDTO;
import org.udg.trackdev.spring.dto.response.*;
import org.udg.trackdev.spring.facade.ProjectFacade;
import org.udg.trackdev.spring.service.AccessChecker;
import org.udg.trackdev.spring.service.ProjectService;
import org.udg.trackdev.spring.service.UserService;
import org.udg.trackdev.spring.utils.ErrorConstants;
import org.udg.trackdev.spring.utils.ValidatorHelper;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "5. Projects")
@RestController
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController extends BaseController {
    @Autowired
    ProjectService service;

    @Autowired
    UserService userService;

    @Autowired
    AccessChecker accessChecker;

    private final ProjectFacade facade;

    private final ValidatorHelper validatorHelper;

    @Operation(summary = "Get all projects", description = "Get all projects")
    @GetMapping
    public Collection<ProjectWithUserResponseDTO> getProjects(Principal principal) {
        return facade.getAllProjects(principal);
    }

    @Operation(summary = "Get specific project", description = "Get specific project")
    @GetMapping(path = "/{projectId}")
    public ProjectCompleteResponseDTO getProject(@PathVariable(name = "projectId") Long projectId, Principal principal) {
        return facade.getProject(projectId, principal);
    }

    @Operation(summary = "Edit specific project", description = "Edit specific project")
    @PatchMapping(path = "/{projectId}")
    public ProjectWithUserResponseDTO editProject(@Valid @RequestBody EditProjectRequestDTO request,
                               @PathVariable(name = "projectId") Long projectId, Principal principal, BindingResult validation) {
        validatorHelper.validateRequest(validation);
        return facade.editProject(projectId, request, principal);
    }

    @Operation(summary = "Delete specific project", description = "Delete specific project")
    @DeleteMapping(path = "/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable(name = "projectId") Long projectId, Principal principal) {
        facade.deleteProject(projectId, principal);
        return okNoContent();
    }

    @Operation(summary = "Get tasks of specific project", description = "Get tasks of specific project")
    @GetMapping(path = "/{projectId}/tasks")
    public ProjectTaskResponseDTO getProjectTasks(@PathVariable(name = "projectId") Long projectId, Principal principal) {
        return facade.getProjectTasks(projectId, principal);

    }

    @Operation(summary = "Create sprint of specific project", description = "Create sprint of specific project")
    @PostMapping(path = "/{projectId}/sprints")
    public ResponseEntity<Void> createProjectSprint(@Valid @RequestBody CreateSprintRequestDTO request, @PathVariable(name = "projectId") Long projectId,
                                                    Principal principal, BindingResult result) {
        if (result.hasErrors()) {
            throw new ControllerException(ErrorConstants.INVALID_SPRINT_NAME_LENGTH);
        }
        facade.createProjectSprint(request, projectId, principal);
        return okNoContent();
    }

    @Operation(summary = "Create US of specific project", description = "Create US of specific project")
    @PostMapping(path = "/{projectId}/tasks")
    public ProjectResponseDTO createTask(@Valid @RequestBody CreateTaskRequestDTO request, @PathVariable(name = "projectId") Long projectId,
                                         Principal principal) {
        return facade.createProjectTask(request, projectId, principal);
    }

    @Operation(summary = "Get all project sprints of specific project", description = "Get all project sprints of specific project")
    @GetMapping(path = "/{projectId}/sprints")
    public List<ProjectSprintsResponseDTO> getProjectSprints(@PathVariable(name = "projectId") Long projectId,
                                                       Principal principal) {
        //List<Map<String, String>> customResponse = buildCustomResponse(service.getProjectSprints(project));
        return facade.getProjectSprints(projectId, principal);
    }

    @Operation(summary = "Get users qualification of specific project", description = "Get users qualification of specific project")
    @GetMapping(path = "/{projectId}/qualification")
    public Map<String, ProjectRankDTO> getProjectRank(@PathVariable(name = "projectId") Long projectId,
                                                                          Principal principal) {
        return facade.getProjectRak(projectId, principal);
    }

    /**
    private List<Map<String, String>> buildCustomResponse(Collection<Sprint> sprints) {
        List<Map<String, String>> customResponse = new ArrayList<>();
        for (Sprint sprint : sprints) {
            Map<String, String> sprintMap = new HashMap<>();
            sprintMap.put("value", sprint.getName());
            sprintMap.put("label", sprint.getName());
            sprintMap.put("id", sprint.getId().toString());
            sprintMap.put("startDate", sprint.getStartDate().toString());
            sprintMap.put("endDate", sprint.getEndDate().toString());
            sprintMap.put("status", sprint.getStatusText());
            customResponse.add(sprintMap);
        }
        return customResponse;
    }
     **/

}