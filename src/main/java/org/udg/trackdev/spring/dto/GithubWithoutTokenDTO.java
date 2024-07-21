package org.udg.trackdev.spring.dto;

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
public class GithubWithoutTokenDTO {

    private String login;

    private String avatar_url;

    private String html_url;

    private String id;

}
