package org.udg.trackdev.spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.response.SprintResponseDTO;
import org.udg.trackdev.spring.entity.Sprint;
import org.udg.trackdev.spring.entity.sprintchanges.SprintChange;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;
import org.udg.trackdev.spring.facade.SprintFacade;
import org.udg.trackdev.spring.dto.request.SprintRequestDTO;
import org.udg.trackdev.spring.service.AccessChecker;
import org.udg.trackdev.spring.service.SprintChangeService;
import org.udg.trackdev.spring.service.SprintService;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "7. Sprints")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/sprints")
public class SprintController extends CrudController<Sprint, SprintService> {

    private final SprintFacade facade;

    @Operation(summary = "Get all sprints", description = "Get all sprints")
    @GetMapping
    public List<SprintResponseDTO> getAllSprints(Principal principal) {
        return facade.getAllSprints(principal);
    }

    @Operation(summary = "Get specific sprint", description = "Get specific sprint")
    @GetMapping(path = "/{id}")
    public SprintResponseDTO getSprint(Principal principal, @PathVariable("id") Long id) {
        return facade.getSprint(principal, id);
    }

    @Operation(summary = "Edit specific sprint", description = "Edit specific sprint")
    @PatchMapping(path = "/{id}")
    public SprintResponseDTO editSprint(@Valid @RequestBody SprintRequestDTO request,
                             @PathVariable(name = "id") Long id, Principal principal) {
        return facade.editSprint(request, id, principal);

    }

    //TODO: Refactor this method
    @Operation(summary = "Get history of logs of the sprint", description = "Get history of logs of the sprint")
    @GetMapping(path = "/{id}/history")
    @JsonView(EntityLevelViews.Basic.class)
    public List<SprintChange> getHistory(Principal principal, @PathVariable("id") Long id,
                                         @RequestParam(value = "search", required = false) String search) {
        String refinedSearch = super.scopedSearch("entityId:"+ id, search);
        Specification<SprintChange> specification = super.buildSpecificationFromSearch(refinedSearch);
        return sprintChangeService.search(specification);
    }

    @Operation(summary = "Delete specific sprint", description = "Delete specific sprint")
    @DeleteMapping(path = "/{id}")
    public void deleteSprint(@PathVariable("id") Long id, Principal principal) {
        facade.deleteSprint(id, principal);
    }

}
