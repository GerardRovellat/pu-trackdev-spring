package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.CreateCourseRequestDTO;
import org.udg.trackdev.spring.dto.request.SubjectRequestDTO;
import org.udg.trackdev.spring.dto.response.SubjectCompleteResponseDTO;

import java.security.Principal;

public interface SubjectFacade {

    SubjectCompleteResponseDTO getSubject(Long id, Principal principal);

    Long createSubject(SubjectRequestDTO request, Principal principal);

    SubjectCompleteResponseDTO editSubject(SubjectRequestDTO request, Long id, Principal principal);

    void deleteSubject(Long id, Principal principal);

    Long createCourseOnSubject(Long subjectId, CreateCourseRequestDTO request, Principal principal);

}
