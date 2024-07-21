package org.udg.trackdev.spring.dto.request.subjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.entity.Subject;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * The type Subject request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectRequestDTO {

    @NotBlank
    @Size(min = Subject.MIN_NAME_LENGTH, max = Subject.NAME_LENGTH,
            message = ErrorConstants.INVALID_SUBJECT_NAME_LENGTH)
    private String name;

    @NotBlank
    @Size(min = Subject.MIN_ACRONYM_LENGTH, max = Subject.MAX_ACRONYM_LENGTH,
            message = ErrorConstants.INVALID_SUBJECT_ACRONYM_LENGTH)
    private String acronym;

}
