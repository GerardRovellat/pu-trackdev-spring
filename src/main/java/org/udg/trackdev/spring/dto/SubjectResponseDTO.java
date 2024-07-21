package org.udg.trackdev.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Subject response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseDTO {

    private String name;

    private String ownerId;

    private String acronym;

}
