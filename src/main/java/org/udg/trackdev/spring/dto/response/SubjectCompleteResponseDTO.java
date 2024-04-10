package org.udg.trackdev.spring.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectCompleteResponseDTO {

    private Long id;

    private String name;

    private String ownerId;

    private String acronym;

    private List<CourseResponseDTO> courses;

}
