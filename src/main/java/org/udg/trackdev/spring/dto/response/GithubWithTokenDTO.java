package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Github dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GithubWithTokenDTO {

    private String login;

    private String avatar_url;

    private String html_url;

    private String github_token;

}
