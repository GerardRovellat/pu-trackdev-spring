package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.courses.CreateProjectRequestDTO;
import org.udg.trackdev.spring.dto.request.courses.EditCourseRequestDTO;
import org.udg.trackdev.spring.dto.response.CourseCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectResponseDTO;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * The interface Course facade.
 */
public interface CourseFacade {

    /**
     * Gets all courses.
     *
     * @param principal the principal
     * @return the all courses
     */
    List<CourseCompleteResponseDTO> getAllCourses(Principal principal);

    /**
     * Gets course.
     *
     * @param id        the id
     * @param principal the principal
     * @return the course
     */
    CourseCompleteResponseDTO getCourse(Long id, Principal principal);

    /**
     * Edit course course complete response dto.
     *
     * @param request   the request
     * @param id        the id
     * @param principal the principal
     * @return the course complete response dto
     */
    CourseCompleteResponseDTO editCourse(EditCourseRequestDTO request, Long id, Principal principal);

    /**
     * Delete course.
     *
     * @param id        the id
     * @param principal the principal
     */
    void deleteCourse(Long id, Principal principal);

    /**
     * Gets projects.
     *
     * @param id        the id
     * @param principal the principal
     * @return the projects
     */
    Collection<ProjectResponseDTO> getProjects(Long id, Principal principal);

    /**
     * Create project long.
     *
     * @param request   the request
     * @param courseId  the course id
     * @param principal the principal
     * @return the long
     */
    Long createProject(CreateProjectRequestDTO request, Long courseId, Principal principal);

}
