package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * The type Sprint response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprintResponseDTO {

    private String name;

    private Date startDate;

    private Date endDate;

    private String status;

    private String statusText;

}
