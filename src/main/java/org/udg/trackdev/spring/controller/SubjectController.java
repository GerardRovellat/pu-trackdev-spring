package org.udg.trackdev.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.request.subjects.CreateCourseRequestDTO;
import org.udg.trackdev.spring.dto.request.subjects.SubjectRequestDTO;
import org.udg.trackdev.spring.dto.response.subjects.SubjectCompleteResponseDTO;
import org.udg.trackdev.spring.facade.SubjectFacade;
import org.udg.trackdev.spring.utils.ErrorConstants;
import org.udg.trackdev.spring.utils.ValidatorHelper;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "3. Subjects")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/subjects")
public class SubjectController extends BaseController {

    private final SubjectFacade facade;

    private final ValidatorHelper validatorHelper;

    //TODO: Refactor this method
    @Operation(summary = "Get all subjects", description = "Get all subjects")
    @GetMapping
    public List<SubjectCompleteResponseDTO> getAllSubjects(Principal principal) {
        return facade.getAllSubjects(principal);
    }

    @Operation(summary = "Get specific subject", description = "Get specific subject")
    @GetMapping(path = "/{id}")
    public SubjectCompleteResponseDTO getSubject(@PathVariable("id") Long id, Principal principal) {
        return facade.getSubject(id, principal);
    }

    @Operation(summary = "Create subject", description = "Create subject")
    @PostMapping
    public Long createSubject(@Valid @RequestBody SubjectRequestDTO request, Principal principal) {
        return facade.createSubject(request, principal);
    }

    @Operation(summary = "Edit specific subject", description = "Edit specific subject")
    @PatchMapping(path = "/{id}")
    public SubjectCompleteResponseDTO editSubject(@Valid @RequestBody SubjectRequestDTO request, @PathVariable("id") Long id,
                                                  Principal principal, BindingResult validation) {
        validatorHelper.validateRequest(validation);
        return facade.editSubject(request, id, principal);
    }

    @Operation(summary = "Delete specific subject", description = "Delete specific subject")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable("id") Long id, Principal principal) {
        facade.deleteSubject(id, principal);
        return okNoContent();
    }

    @Operation(summary = "Create course enrolled to specific subject", description = "Create course enrolled to specific subject")
    @PostMapping(path = "/{id}/courses")
    public Long createCourse(@Valid @RequestBody CreateCourseRequestDTO request, @PathVariable("id") Long id,
                             Principal principal, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new ControllerException(ErrorConstants.INVALID_COURSE_START_YEAR);
        }
        return facade.createCourseOnSubject(id, request, principal);
    }

}