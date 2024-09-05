package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Subject complete response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectCompleteResponseDTO {

    private Long id;

    private String name;

    private String ownerId;

    private String acronym;

    private List<CourseDTO> courses;

}
