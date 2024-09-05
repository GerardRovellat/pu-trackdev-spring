package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Project rank dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRankDTO {

    private String name;

    private String acronym;

    private String color;

    private String qualification;

}
