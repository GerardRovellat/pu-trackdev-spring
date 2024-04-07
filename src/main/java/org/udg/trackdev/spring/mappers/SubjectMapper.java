package org.udg.trackdev.spring.mappers;

import org.udg.trackdev.spring.dto.response.SubjectResponseDTO;
import org.udg.trackdev.spring.entity.Subject;

public interface SubjectMapper {

    SubjectResponseDTO toSubjectResponseDTO(Subject subject);

}
