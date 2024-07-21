package org.udg.trackdev.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Course response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private Long id;

    private Integer startYear;

    private String githubOrganization;

    private SubjectProjectResponseDTO subject;

}
