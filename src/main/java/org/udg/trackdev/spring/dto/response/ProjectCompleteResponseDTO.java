package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * The type Project complete response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCompleteResponseDTO {

    private Long id;

    private String name;

    private Double qualification;

    private CourseCompleteResponseDTO course;

    private Collection<UserDTO> members;

    private Collection<SprintResponseDTO> sprints;

    private Collection<TaskResponseDTO> tasks;

}
