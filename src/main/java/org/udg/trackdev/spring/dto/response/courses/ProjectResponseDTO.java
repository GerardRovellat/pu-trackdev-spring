package org.udg.trackdev.spring.dto.response.courses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.dto.CourseDTO;

/**
 * The type Project response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDTO {

    private Long id;

    private String name;

    private CourseDTO course;

    private Double qualification;

}
