package org.udg.trackdev.spring.mappers;

import org.udg.trackdev.spring.dto.response.SubjectCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.SubjectResponseDTO;
import org.udg.trackdev.spring.entity.Subject;

public interface SubjectMapper {

    SubjectCompleteResponseDTO toSubjectCompleteResponseDTO(Subject subject);

    SubjectResponseDTO toSubjectResponseDTO(Subject subject);

}
