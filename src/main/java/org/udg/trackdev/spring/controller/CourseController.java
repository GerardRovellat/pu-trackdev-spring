package org.udg.trackdev.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.request.courses.CreateProjectRequestDTO;
import org.udg.trackdev.spring.dto.request.courses.EditCourseRequestDTO;
import org.udg.trackdev.spring.dto.response.CourseCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectResponseDTO;
import org.udg.trackdev.spring.facade.CourseFacade;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;

/**
 * The type Course controller.
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "4. Courses")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/courses")
public class CourseController extends BaseController {

    private final CourseFacade facade;

    /**
     * Gets courses.
     *
     * @param principal the principal
     * @return the courses
     */
    @Operation(summary = "Get all courses", description = "Get all courses")
    @GetMapping
    public Collection<CourseCompleteResponseDTO> getCourses(Principal principal) {
        return facade.getAllCourses(principal);
    }

    /**
     * Gets course.
     *
     * @param courseId  the course id
     * @param principal the principal
     * @return the course
     */
    @Operation(summary = "Get specific course", description = "Get specific course")
    @GetMapping(path = "/{courseId}")
    public CourseCompleteResponseDTO getCourse(@PathVariable("courseId") Long courseId, Principal principal) {
        return facade.getCourse(courseId, principal);
    }

    /**
     * Edit course course complete response dto.
     *
     * @param request    the request
     * @param courseId   the course id
     * @param principal  the principal
     * @param validation the validation
     * @return the course complete response dto
     */
    @Operation(summary = "Edit specific course", description = "Edit specific course")
    @PatchMapping(path = "/{courseId}")
    public CourseCompleteResponseDTO editCourse(@Valid @RequestBody EditCourseRequestDTO request,
                             @PathVariable("courseId") Long courseId, Principal principal, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new ControllerException(ErrorConstants.INVALID_COURSE_START_YEAR);
        }
        return facade.editCourse(request, courseId, principal);
    }

    /**
     * Delete course response entity.
     *
     * @param courseId  the course id
     * @param principal the principal
     * @return the response entity
     */
    @Operation(summary = "Delete specific course", description = "Delete specific course")
    @DeleteMapping(path = "/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("courseId") Long courseId, Principal principal) {
        facade.deleteCourse(courseId, principal);
        return okNoContent();
    }

    /**
     * Gets projects.
     *
     * @param courseId  the course id
     * @param principal the principal
     * @return the projects
     */
    @Operation(summary = "Get projects enrolled to specific course", description = "Get projects enrolled to specific course")
    @GetMapping(path = "/{courseId}/projects")
    public Collection<ProjectResponseDTO> getProjects(@PathVariable("courseId") Long courseId, Principal principal) {
        return facade.getProjects(courseId, principal);
    }

    /**
     * Create project long.
     *
     * @param request    the request
     * @param courseId   the course id
     * @param principal  the principal
     * @param validation the validation
     * @return the long
     */
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