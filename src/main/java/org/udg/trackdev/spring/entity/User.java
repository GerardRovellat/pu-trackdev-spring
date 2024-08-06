package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.udg.trackdev.spring.configuration.UserType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * The type User.
 */
@Entity
@Table(name = "users")
public class User extends BaseEntityUUID {

    /**
     * The constant MIN_USERNAME_LENGTH.
     */
    public static final int MIN_USERNAME_LENGTH = 1;
    /**
     * The constant USERNAME_LENGTH.
     */
    public static final int USERNAME_LENGTH = 50;
    /**
     * The constant MIN_EMAIL_LENGHT.
     */
    public static final int MIN_EMAIL_LENGHT = 4;
    /**
     * The constant EMAIL_LENGTH.
     */
    public static final int EMAIL_LENGTH = 128;
    /**
     * The constant CAPITAL_LETTERS_LENGTH.
     */
    public static final int CAPITAL_LETTERS_LENGTH = 2;

    /**
     * Instantiates a new User.
     */
    public User() {}

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
  @Column(length=USERNAME_LENGTH)
  private String username;

  @NotNull
  @Column(unique=true, length=EMAIL_LENGTH)
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

  @Size(min = CAPITAL_LETTERS_LENGTH, max = CAPITAL_LETTERS_LENGTH)
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

 // -- GETTERS AND SETTERS

  public String getId() {
    return super.getId();
  }

    /**
     * Gets email.
     *
     * @return the email
     */
  public String getEmail() {
    return email;
  }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
    this.email = email;
  }

    /**
     * Gets username.
     *
     * @return the username
     */
  public String getUsername() {
    return username;
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
     * Gets current project.
     *
     * @return the current project
     */
  public Long getCurrentProject() { return currentProject; }

    /**
     * Sets current project.
     *
     * @param currentProject the current project
     */
    public void setCurrentProject(Long currentProject) { this.currentProject = currentProject; }

    /**
     * Gets password.
     *
     * @return the password
     */
    @JsonIgnore
  public String getPassword() {
    return password;
  }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
    this.password = password;
  }

    /**
     * Gets recovery code.
     *
     * @return the recovery code
     */
    @JsonIgnore
  public String getRecoveryCode() { return recoveryCode; }

    /**
     * Sets recovery code.
     *
     * @param recoveryCode the recovery code
     */
    public void setRecoveryCode(String recoveryCode) { this.recoveryCode = recoveryCode; }

    /**
     * Gets roles.
     *
     * @return the roles
     */
  public Set<Role> getRoles() { return roles; }

    /**
     * Gets color.
     *
     * @return the color
     */
  public String getColor() { return color; }

    /**
     * Sets color.
     *
     * @param color the color
     * @return the color
     */
    public String setColor(String color) { return this.color = color; }

    /**
     * Gets capital letters.
     *
     * @return the capital letters
     */
  public String getCapitalLetters() { return capitalLetters; }

    /**
     * Gets projects.
     *
     * @return the projects
     */
  public Collection<Project> getProjects() {
    return projects;
  }

    /**
     * Gets github info.
     *
     * @return the github info
     */
  public GithubInfo getGithubInfo() { return githubInfo; }

    /**
     * Gets points review list.
     *
     * @return the points review list
     */
    public List<PointsReview> getPointsReviewList() { return pointsReviewList; }

    /**
     * Add points review.
     *
     * @param pointsReview the points review
     */
    public void addPointsReview(PointsReview pointsReview) { this.pointsReviewList.add(pointsReview); }

    /**
     * Sets github token.
     *
     * @param githubToken the github token
     * @return the github token
     */
    public String setGithubToken(String githubToken) { return githubInfo.setGithubToken(githubToken); }

    /**
     * Sets github name.
     *
     * @param login the login
     */
    public void setGithubName(String login) { githubInfo.setLogin(login); }

    /**
     * Sets github avatar.
     *
     * @param githubAvatar the github avatar
     */
    public void setGithubAvatar(String githubAvatar) { githubInfo.setAvatar_url(githubAvatar); }

    /**
     * Sets github html url.
     *
     * @param githubHtmlUrl the github html url
     */
    public void setGithubHtmlUrl(String githubHtmlUrl) { githubInfo.setHtml_url(githubHtmlUrl); }

    /**
     * Sets capital letters.
     *
     * @param capitalLetters the capital letters
     * @return the capital letters
     */
    public String setCapitalLetters(String capitalLetters) { return this.capitalLetters = capitalLetters; }

    /**
     * Gets change password.
     *
     * @return the change password
     */
  public Boolean getChangePassword() { return changePassword; }

    /**
     * Sets change password.
     *
     * @param changePassword the change password
     */
    public void setChangePassword(Boolean changePassword) { this.changePassword = changePassword; }

    /**
     * Gets enabled.
     *
     * @return the enabled
     */
  public Boolean getEnabled() { return enabled; }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(Boolean enabled) { this.enabled = enabled; }

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
    for(Role role: roles) {
      if(role.getUserType() == userType) {
        inRole = true;
        break;
      }
    }
    return inRole;
  }

    /**
     * Get last login date.
     *
     * @return the date
     */
  public Date getLastLogin(){ return lastLogin; }

    /**
     * Sets last login.
     *
     * @param lastLogin the last login
     */
    public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

    /**
     * Add own course.
     *
     * @param subject the subject
     */
    public void addOwnCourse(Subject subject) { subjectsOwns.add(subject); }

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
    if(this.projects.contains(project)) {
      this.projects.remove(project);
    }
  }

  private String randomColorGenerator(){
    int red = this.random.nextInt(256);
    int green = this.random.nextInt(256);
    int blue = this.random.nextInt(256);
    return String.format("#%02x%02x%02x", red, green, blue);
  }

  private static String generateCapitalLetters(String username){
    String[] names = username.split(" ");
    String firstLetter;
    String secondLetter;
    if(names.length > 1){
      firstLetter = names[0].substring(0, 1);
      secondLetter = names[1].substring(0, 1);
    }
    else{
      firstLetter = names[0].substring(0, 1);
      secondLetter = names[0].substring(1, 2);
    }
    return (firstLetter + secondLetter).toUpperCase();
  }

}
