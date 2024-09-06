package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The type Github info.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "github_users_info")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubInfo extends BaseEntityUUID{

    @OneToOne(mappedBy = "githubInfo")
    private User user;

    private String github_token;

    private String login;

    private String avatar_url;

    private String html_url;

    /**
     * Sets github token.
     *
     * @param githubToken the github token
     */
    public void setGithubToken(String githubToken) {
        this.github_token = githubToken;
    }

}
