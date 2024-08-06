package org.udg.trackdev.spring.dto.response.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.dto.response.common.GithubWithTokenDTO;
import org.udg.trackdev.spring.dto.response.courses.ProjectResponseDTO;

import java.util.List;

/**
 * The type User dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String id;

    private String username;

    private String email;

    private List<String> roles;

    private String color;

    private String capitalLetters;

    private Long currentProject;

    private boolean changePassword;

    private boolean enabled;

    private GithubWithTokenDTO githubInfo;

    private List<ProjectResponseDTO> projects;

}
