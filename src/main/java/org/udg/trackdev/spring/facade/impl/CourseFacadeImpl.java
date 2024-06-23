package org.udg.trackdev.spring.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.dto.request.CreateProjectRequestDTO;
import org.udg.trackdev.spring.dto.request.EditCourseRequestDTO;
import org.udg.trackdev.spring.dto.response.CourseCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectResponseDTO;
import org.udg.trackdev.spring.entity.Course;
import org.udg.trackdev.spring.facade.CourseFacade;
import org.udg.trackdev.spring.mappers.EntityMapper;
import org.udg.trackdev.spring.service.AccessChecker;
import org.udg.trackdev.spring.service.AuthService;
import org.udg.trackdev.spring.service.CourseService;
import org.udg.trackdev.spring.service.ProjectService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseFacadeImpl implements CourseFacade {

    private final AuthService authService;

    private final AccessChecker accessChecker;

    private final CourseService courseService;

    private final ProjectService projectService;

    private final EntityMapper mapper;

    @Override
    public List<CourseCompleteResponseDTO> getAllCourses(Principal principal) {
        accessChecker.checkCanViewAllCourses(authService.getLoggedInUserId(principal));
        return courseService.getAll().stream().map(mapper::courseEntityToCompleteDTO).collect(Collectors.toList());
    }

    @Override
    public CourseCompleteResponseDTO getCourse(Long id, Principal principal) {
        Course course = courseService.get(id);
        accessChecker.checkCanViewCourse(course, authService.getLoggedInUserId(principal));
        return mapper.courseEntityToCompleteDTO(course);
    }

    @Override
    public CourseCompleteResponseDTO editCourse(EditCourseRequestDTO request, Long id, Principal principal) {
        return mapper.courseEntityToCompleteDTO(courseService.editCourse(id, request.getStartYear(),
                request.getSubjectId(), request.getGithubOrganization(), authService.getLoggedInUserId(principal)));
    }

    @Override
    public void deleteCourse(Long id, Principal principal) {
        courseService.deleteCourse(id, authService.getLoggedInUserId(principal));
    }

    @Override
    public Collection<ProjectResponseDTO> getProjects(Long id, Principal principal) {
        String userId = authService.getLoggedInUserId(principal);
        Course course = courseService.get(id);
        if(accessChecker.canViewCourseAllProjects(course, userId)) {
            return course.getProjects().stream().map(mapper::projectEntityToDTO).collect(Collectors.toList());
        } else {
            return course.getProjects().stream().filter(group -> group.isMember(userId))
                    .map(mapper::projectEntityToDTO).collect(Collectors.toList());
        }
    }

    @Override
    public Long createProject(CreateProjectRequestDTO request, Long courseId, Principal principal) {
        String userId = authService.getLoggedInUserId(principal);
        return projectService.createProject(request.getName(), request.getMembers(), courseId, userId).getId();
    }


}
