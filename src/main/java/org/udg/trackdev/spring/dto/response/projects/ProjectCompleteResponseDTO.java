package org.udg.trackdev.spring.dto.response.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.dto.response.courses.CourseCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.sprints.SprintResponseDTO;
import org.udg.trackdev.spring.dto.response.tasks.TaskResponseDTO;
import org.udg.trackdev.spring.dto.response.users.UserDTO;

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
