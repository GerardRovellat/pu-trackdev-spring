package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;
import org.udg.trackdev.spring.entity.views.PrivacyLevelViews;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The type Github info.
 */
@Entity
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
     * Instantiates a new Github info.
     */
    public GithubInfo() {}

    /**
     * Gets github token.
     *
     * @return the github token
     */
    @JsonView({PrivacyLevelViews.Private.class, EntityLevelViews.UserWithGithubToken.class})
    public String getGithub_token() { return github_token; }

    /**
     * Sets github token.
     *
     * @param githubToken the github token
     * @return the github token
     */
    public String setGithubToken(String githubToken) { return this.github_token = githubToken; }

    /**
     * Gets login.
     *
     * @return the login
     */
    @JsonView({PrivacyLevelViews.Public.class, EntityLevelViews.Basic.class})
    public String getLogin() { return login; }

    /**
     * Sets login.
     *
     * @param login the login
     * @return the login
     */
    public String setLogin(String login) { return this.login = login; }

    /**
     * Gets avatar url.
     *
     * @return the avatar url
     */
    @JsonView({PrivacyLevelViews.Public.class, EntityLevelViews.Basic.class})
    public String getAvatar_url() { return avatar_url; }

    /**
     * Sets avatar url.
     *
     * @param avatar_url the avatar url
     * @return the avatar url
     */
    public String setAvatar_url(String avatar_url) { return this.avatar_url = avatar_url; }

    /**
     * Gets html url.
     *
     * @return the html url
     */
    @JsonView({PrivacyLevelViews.Public.class, EntityLevelViews.Basic.class})
    public String getHtml_url() { return html_url; }

    /**
     * Sets html url.
     *
     * @param html_url the html url
     * @return the html url
     */
    public String setHtml_url(String html_url) { return this.html_url = html_url; }

}
