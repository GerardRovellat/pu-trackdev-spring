package org.udg.trackdev.spring.entity;

import lombok.*;
import org.udg.trackdev.spring.configuration.UserType;
import org.udg.trackdev.spring.utils.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * The type User.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntityUUID {

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param email    the email
     * @param password the password
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.color = randomColorGenerator();
        this.githubInfo = new GithubInfo();
        this.capitalLetters = generateCapitalLetters(username);
    }

    @NotNull
    @Column(length = Constants.MAX_USERNAME_LENGTH)
    private String username;

    @NotNull
    @Column(unique = true, length = Constants.MAX_EMAIL_LENGTH)
    private String email;

    @NotNull
    private String password;

    private Date lastLogin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Collection<Subject> subjectsOwns = new ArrayList<>();

    @ManyToMany(mappedBy = "members")
    private Collection<Project> projects = new ArrayList<>();

    @ManyToMany()
    private Set<Role> roles = new HashSet<>();

    private String color;

    @Size(min = Constants.CAPITAL_LETTERS_LENGTH, max = Constants.CAPITAL_LETTERS_LENGTH)
    private String capitalLetters;

    private Long currentProject;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author", fetch = FetchType.LAZY)
    private final Collection<Comment> comments = new ArrayList<>();

    @NotNull
    private Boolean changePassword;

    @NotNull
    private Boolean enabled;

    private String recoveryCode;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "githubInfoId", referencedColumnName = "id")
    private GithubInfo githubInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PointsReview> pointsReviewList = new ArrayList<>();

    private Random random = new Random();

    public String getId() {
        return super.getId();
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
        this.capitalLetters = generateCapitalLetters(username);
    }

    /**
     * Add points review.
     *
     * @param pointsReview the points review
     */
    public void addPointsReview(PointsReview pointsReview) {
        this.pointsReviewList.add(pointsReview);
    }

    /**
     * Sets github token.
     *
     * @param githubToken the github token
     */
    public void setGithubToken(String githubToken) {
        githubInfo.setGithubToken(githubToken);
    }

    /**
     * Sets github name.
     *
     * @param login the login
     */
    public void setGithubName(String login) {
        githubInfo.setLogin(login);
    }

    /**
     * Sets github avatar.
     *
     * @param githubAvatar the github avatar
     */
    public void setGithubAvatar(String githubAvatar) {
        githubInfo.setAvatar_url(githubAvatar);
    }

    /**
     * Sets github html url.
     *
     * @param githubHtmlUrl the github html url
     */
    public void setGithubHtmlUrl(String githubHtmlUrl) {
        githubInfo.setHtml_url(githubHtmlUrl);
    }

    /**
     * Add role.
     *
     * @param role the role
     */
    public void addRole(Role role) {
        roles.add(role);
    }

    /**
     * Is user type boolean.
     *
     * @param userType the user type
     * @return the boolean
     */
    public boolean isUserType(UserType userType) {
        boolean inRole = false;
        for (Role role : roles) {
            if (role.getUserType() == userType) {
                inRole = true;
                break;
            }
        }
        return inRole;
    }

    /**
     * Add own course.
     *
     * @param subject the subject
     */
    public void addOwnCourse(Subject subject) {
        subjectsOwns.add(subject);
    }

    /**
     * Add to group.
     *
     * @param project the project
     */
    public void addToGroup(Project project) {
        this.projects.add(project);
    }

    /**
     * Remove from group.
     *
     * @param project the project
     */
    public void removeFromGroup(Project project) {
        if (this.projects.contains(project)) {
            this.projects.remove(project);
        }
    }

    private String randomColorGenerator() {
        int red = this.random.nextInt(256);
        int green = this.random.nextInt(256);
        int blue = this.random.nextInt(256);
        return String.format("#%02x%02x%02x", red, green, blue);
    }

    private static String generateCapitalLetters(String username) {
        String[] names = username.split(" ");
        String firstLetter;
        String secondLetter;
        if (names.length > 1) {
            firstLetter = names[0].substring(0, 1);
            secondLetter = names[1].substring(0, 1);
        } else {
            firstLetter = names[0].substring(0, 1);
            secondLetter = names[0].substring(1, 2);
        }
        return (firstLetter + secondLetter).toUpperCase();
    }

}
