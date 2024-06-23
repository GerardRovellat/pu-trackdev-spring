package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
