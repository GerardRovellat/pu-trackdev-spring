package org.udg.trackdev.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GithubDTO {


    private String login;

    private String avatar_url;

    private String html_url;

    private String github_token;

    private String id;

}
