package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GithubPullRequestDTO {

    private int number;

    private String title;

    private String state;

    private String html_url;

    private GithubWithoutTokenDTO user;

}
