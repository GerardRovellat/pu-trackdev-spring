package org.udg.trackdev.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.request.CreateProjectRequestDTO;
import org.udg.trackdev.spring.dto.request.EditCourseRequestDTO;
import org.udg.trackdev.spring.dto.response.CourseCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectResponseDTO;
import org.udg.trackdev.spring.facade.CourseFacade;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "4. Courses")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/courses")
public class CourseController extends BaseController {

    private final CourseFacade facade;

    @Operation(summary = "Get all courses", description = "Get all courses")
    @GetMapping
    public Collection<CourseCompleteResponseDTO> getCourses(Principal principal) {
        return facade.getAllCourses(principal);
    }

    @Operation(summary = "Get specific course", description = "Get specific course")
    @GetMapping(path = "/{courseId}")
    public CourseCompleteResponseDTO getCourse(@PathVariable("courseId") Long courseId, Principal principal) {
        return facade.getCourse(courseId, principal);
    }

    @Operation(summary = "Edit specific course", description = "Edit specific course")
    @PatchMapping(path = "/{courseId}")
    public CourseCompleteResponseDTO editCourse(@Valid @RequestBody EditCourseRequestDTO request,
                             @PathVariable("courseId") Long courseId, Principal principal, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new ControllerException(ErrorConstants.INVALID_COURSE_START_YEAR);
        }
        return facade.editCourse(request, courseId, principal);
    }

    @Operation(summary = "Delete specific course", description = "Delete specific course")
    @DeleteMapping(path = "/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") Long courseId, Principal principal) {
        facade.deleteCourse(courseId, principal);
        return okNoContent();
    }

    @Operation(summary = "Get projects enrolled to specific course", description = "Get projects enrolled to specific course")
    @GetMapping(path = "/{courseId}/projects")
    public Collection<ProjectResponseDTO> getProjects(@PathVariable("courseId") Long courseId, Principal principal) {
        return facade.getProjects(courseId, principal);
    }

    @Operation(summary = "Create project enrolled to specific course", description = "Create project enrolled to specific course")
    @PostMapping(path = "/{courseId}/projects")
    public Long createProject(@Valid @RequestBody CreateProjectRequestDTO request, @PathVariable("courseId") Long courseId,
                              Principal principal, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new ControllerException(ErrorConstants.INVALID_PRJ_NAME_LENGTH);
        }
        return facade.createProject(request,courseId,principal);
    }

}