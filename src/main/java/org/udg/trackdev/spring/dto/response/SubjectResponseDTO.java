package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponseDTO {

    private String name;

    private String ownerId;

    private String acronym;

    private List<CourseResponseDTO> courses;

}
