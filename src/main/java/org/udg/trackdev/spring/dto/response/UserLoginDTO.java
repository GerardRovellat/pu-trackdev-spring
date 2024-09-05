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
public class UserLoginDTO {

    private String id;

    private String username;

    private String email;

    private String lastLogin;

    private List<String> roles;

    private String color;

    private String capitalLetters;

    private Long currentProject;

    private boolean changePassword;

    private boolean enabled;

    private GithubWithTokenDTO githubInfo;

}
