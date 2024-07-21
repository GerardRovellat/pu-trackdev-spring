package org.udg.trackdev.spring.dto.response.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.dto.CourseDTO;
import org.udg.trackdev.spring.dto.response.users.UserDTO;

import java.util.Collection;

/**
 * The type Project with user response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectWithUserResponseDTO {

    private Long id;

    private String name;

    private CourseDTO course;

    private Double qualification;

    private Collection<UserDTO> members;

}
