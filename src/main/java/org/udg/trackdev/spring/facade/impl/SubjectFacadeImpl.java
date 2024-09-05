package org.udg.trackdev.spring.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.request.subjects.CreateCourseRequestDTO;
import org.udg.trackdev.spring.dto.request.subjects.SubjectRequestDTO;
import org.udg.trackdev.spring.dto.response.SubjectCompleteResponseDTO;
import org.udg.trackdev.spring.entity.Subject;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.facade.SubjectFacade;
import org.udg.trackdev.spring.mappers.EntityMapper;
import org.udg.trackdev.spring.service.*;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Subject facade.
 */
@Component
@RequiredArgsConstructor
public class SubjectFacadeImpl implements SubjectFacade {

    private final SubjectService subjectService;

    private final UserService userService;

    private final AuthService authService;

    private final CourseService courseService;

    private final AccessChecker accessChecker;

    private final EntityMapper mapper;

    @Override
    public List<SubjectCompleteResponseDTO> getAllSubjects(Principal principal) {
        User user = userService.get(authService.getLoggedInUserId(principal));
        if (accessChecker.isUserAdmin(user)) {
            return subjectService.getAll().stream().map(mapper::subjectEntityToCompleteDTO).collect(Collectors.toList());
        } else {
            throw new ControllerException(ErrorConstants.UNAUTHORIZED);
        }
    }

    @Override
    public SubjectCompleteResponseDTO getSubject(Long id, Principal principal) {
        Subject subject = subjectService.getSubject(id);
        accessChecker.checkCanViewSubject(subject, principal.getName());
        return mapper.subjectEntityToCompleteDTO(subject);
    }

    @Override
    public Long createSubject(SubjectRequestDTO request, Principal principal) {
        return subjectService.createSubject(request.getName(), request.getAcronym(), principal.getName()).getId();
    }

    @Override
    public SubjectCompleteResponseDTO editSubject(SubjectRequestDTO request, Long id, Principal principal) {
        return mapper.subjectEntityToCompleteDTO(subjectService.editSubjectDetails(id, request.getName(),
                request.getAcronym(), principal.getName()));
    }

    @Override
    public void deleteSubject(Long id, Principal principal) {
        subjectService.deleteSubject(id, principal.getName());
    }

    @Override
    public Long createCourseOnSubject(Long subjectId, CreateCourseRequestDTO request, Principal principal) {
        return courseService.createCourse(subjectId, request.getStartYear(), request.getGithubOrganization(), principal.getName()).getId();
    }
}
