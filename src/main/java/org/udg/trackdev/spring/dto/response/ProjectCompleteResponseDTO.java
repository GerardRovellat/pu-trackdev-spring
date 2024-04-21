package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.dto.UserDTO;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCompleteResponseDTO {

    private Long id;

    private String name;

    private Double qualification;

    private CourseResponseDTO course;

    private Collection<UserDTO> members;

    private Collection<SprintResponseDTO> sprints;

    private Collection<TaskResponseDTO> tasks;

}
