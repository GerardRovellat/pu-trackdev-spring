package org.udg.trackdev.spring.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.dto.request.CourseRequestDTO;
import org.udg.trackdev.spring.dto.request.SubjectRequestDTO;
import org.udg.trackdev.spring.dto.response.SubjectCompleteResponseDTO;
import org.udg.trackdev.spring.entity.Subject;
import org.udg.trackdev.spring.facade.SubjectFacade;
import org.udg.trackdev.spring.mappers.EntityMapper;
import org.udg.trackdev.spring.service.AccessChecker;
import org.udg.trackdev.spring.service.CourseService;
import org.udg.trackdev.spring.service.SubjectService;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class SubjectFacadeImpl implements SubjectFacade {

    private final SubjectService subjectService;

    private final CourseService courseService;

    private final AccessChecker accessChecker;

    private final EntityMapper mapper;

    @Override
    public SubjectCompleteResponseDTO getSubject(Long id, Principal principal) {
        Subject subject = subjectService.getSubject(id);
        accessChecker.checkCanViewSubject(subject, principal.getName());
        return mapper.toSubjectEntityToCompleteDTO(subject);
    }

    @Override
    public Long createSubject(SubjectRequestDTO request, Principal principal) {
        return subjectService.createSubject(request.getName(), request.getAcronym(), principal.getName()).getId();
    }

    @Override
    public SubjectCompleteResponseDTO editSubject(SubjectRequestDTO request, Long id, Principal principal) {
        return mapper.toSubjectEntityToCompleteDTO(subjectService.editSubjectDetails(id, request.getName(),
                request.getAcronym(), principal.getName()));
    }

    @Override
    public void deleteSubject(Long id, Principal principal) {
        subjectService.deleteSubject(id, principal.getName());
    }

    @Override
    public Long createCourseOnSubject(Long subjectId, CourseRequestDTO request, Principal principal) {
        return courseService.createCourse(subjectId, request.getStartYear(), request.getGithubOrganization(), principal.getName()).getId();
    }
}
