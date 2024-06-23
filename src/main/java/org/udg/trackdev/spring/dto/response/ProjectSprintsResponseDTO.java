package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
