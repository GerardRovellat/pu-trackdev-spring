package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.CreateProjectRequestDTO;
import org.udg.trackdev.spring.dto.request.EditCourseRequestDTO;
import org.udg.trackdev.spring.dto.response.CourseCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectResponseDTO;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

public interface CourseFacade {

    List<CourseCompleteResponseDTO> getAllCourses(Principal principal);

    CourseCompleteResponseDTO getCourse(Long id, Principal principal);

    CourseCompleteResponseDTO editCourse(EditCourseRequestDTO request, Long id, Principal principal);

    void deleteCourse(Long id, Principal principal);

    Collection<ProjectResponseDTO> getProjects(Long id, Principal principal);

    Long createProject(CreateProjectRequestDTO request, Long courseId, Principal principal);

}
