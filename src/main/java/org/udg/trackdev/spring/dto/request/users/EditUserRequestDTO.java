package org.udg.trackdev.spring.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Edit user request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditUserRequestDTO {

    private String username;

    private String color;

    private String capitalLetters;

    private Boolean changePassword;

    private String githubToken;

    private Boolean enabled;

}
