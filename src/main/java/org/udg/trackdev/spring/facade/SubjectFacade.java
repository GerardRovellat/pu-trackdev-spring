package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.request.subjects.CreateCourseRequestDTO;
import org.udg.trackdev.spring.dto.request.subjects.SubjectRequestDTO;
import org.udg.trackdev.spring.dto.response.SubjectCompleteResponseDTO;

import java.security.Principal;
import java.util.List;

/**
 * The interface Subject facade.
 */
public interface SubjectFacade {


    List<SubjectCompleteResponseDTO> getAllSubjects(Principal principal);

    /**
     * Gets subject.
     *
     * @param id        the id
     * @param principal the principal
     * @return the subject
     */
    SubjectCompleteResponseDTO getSubject(Long id, Principal principal);

    /**
     * Create subject long.
     *
     * @param request   the request
     * @param principal the principal
     * @return the long
     */
    Long createSubject(SubjectRequestDTO request, Principal principal);

    /**
     * Edit subject subject complete response dto.
     *
     * @param request   the request
     * @param id        the id
     * @param principal the principal
     * @return the subject complete response dto
     */
    SubjectCompleteResponseDTO editSubject(SubjectRequestDTO request, Long id, Principal principal);

    /**
     * Delete subject.
     *
     * @param id        the id
     * @param principal the principal
     */
    void deleteSubject(Long id, Principal principal);

    /**
     * Create course on subject long.
     *
     * @param subjectId the subject id
     * @param request   the request
     * @param principal the principal
     * @return the long
     */
    Long createCourseOnSubject(Long subjectId, CreateCourseRequestDTO request, Principal principal);

}
