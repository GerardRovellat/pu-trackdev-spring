package org.udg.trackdev.spring.dto.response.courses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.dto.SubjectResponseDTO;

/**
 * The type Course complete response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseCompleteResponseDTO {

    private Long id;

    private Integer startYear;

    private String githubOrganization;

    private SubjectResponseDTO subject;

}
