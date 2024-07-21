package org.udg.trackdev.spring.dto.response.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Project sprints response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSprintsResponseDTO {

    private String value;

    private String label;

    private String description;

    private String startDate;

    private String endDate;

    private String status;

}
