package org.udg.trackdev.spring.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.service.Global;

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
