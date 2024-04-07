package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.CourseRequestDTO;
import org.udg.trackdev.spring.dto.request.SubjectRequestDTO;
import org.udg.trackdev.spring.dto.response.SubjectResponseDTO;

import java.security.Principal;

public interface SubjectFacade {

    SubjectResponseDTO getSubject(Long id, Principal principal);

    Long createSubject(SubjectRequestDTO request, Principal principal);

    SubjectResponseDTO editSubject(SubjectRequestDTO request, Long id, Principal principal);

    void deleteSubject(Long id, Principal principal);

    Long createCourseOnSubject(Long subjectId, CourseRequestDTO request, Principal principal);

}
