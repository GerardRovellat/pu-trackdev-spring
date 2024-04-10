package org.udg.trackdev.spring.controller;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udg.trackdev.spring.configuration.UserType;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.request.CourseRequestDTO;
import org.udg.trackdev.spring.dto.request.SubjectRequestDTO;
import org.udg.trackdev.spring.dto.response.SubjectCompleteResponseDTO;
import org.udg.trackdev.spring.entity.Subject;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;
import org.udg.trackdev.spring.facade.SubjectFacade;
import org.udg.trackdev.spring.service.SubjectService;
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
public class SubjectController extends CrudController<Subject, SubjectService> {

    private final SubjectFacade facade;

    private final ValidatorHelper validatorHelper = new ValidatorHelper();

    //TODO: Refactor this method
    /**@Operation(summary = "Get all subjects", description = "Get all subjects")
    @GetMapping
    @JsonView(EntityLevelViews.SubjectComplete.class)
    public List<Subject> search(Principal principal, @RequestParam(value = "search", required = false) String search) {
        String userId = super.getUserId(principal);
        User user = userService.get(userId);
        if (user.isUserType(UserType.ADMIN)){
            return super.search(search);
        }
        else {
            String refinedSearch = super.scopedSearch("ownerId:" + userId, search);
            return super.search(refinedSearch);
        }
    }**/

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
    public Long createCourse(@Valid @RequestBody CourseRequestDTO request, @PathVariable("id") Long id,
                                     Principal principal, BindingResult result) {
        if (result.hasErrors()) {
            throw new ControllerException(ErrorConstants.INVALID_COURSE_START_YEAR);
        }
        return facade.createCourseOnSubject(id, request, principal);
    }

}