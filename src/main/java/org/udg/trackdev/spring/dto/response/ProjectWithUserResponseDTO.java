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
public class ProjectWithUserResponseDTO {

    private Long id;

    private String name;

    private CourseResponseDTO course;

    private Double qualification;

    private Collection<UserDTO> members;

}
