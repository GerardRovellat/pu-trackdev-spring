package org.udg.trackdev.spring.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.trackdev.spring.configuration.UserType;
import org.udg.trackdev.spring.controller.exceptions.EntityNotFound;
import org.udg.trackdev.spring.controller.exceptions.SecurityException;
import org.udg.trackdev.spring.controller.exceptions.ServiceException;
import org.udg.trackdev.spring.entity.GithubInfo;
import org.udg.trackdev.spring.entity.Project;
import org.udg.trackdev.spring.entity.Role;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.repository.UserRepository;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
public class UserService extends BaseServiceUUID<User, UserRepository> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private GithubService githubService;

    @Autowired
    private AccessChecker accessChecker;

    /**
     * The Global.
     */
    @Autowired
    Global global;

    /**
     * Match password user.
     *
     * @param email    the email
     * @param password the password
     * @return the user
     */
    public User matchPassword(String email, String password) {
        User user = this.getByEmail(email);

        if (user == null) throw new ServiceException(ErrorConstants.LOGIN_KO);
        if (!user.getEnabled()) throw new SecurityException(ErrorConstants.USER_DISABLED);

        if (global.getPasswordEncoder().matches(password, user.getPassword()))
            return user;
        else
            throw new ServiceException(ErrorConstants.LOGIN_KO);
    }

    /**
     * Match recovery code boolean.
     *
     * @param user the user
     * @param code the code
     * @return the boolean
     */
    public boolean matchRecoveryCode(User user, String code) {
        if (global.getPasswordEncoder().matches(code, user.getRecoveryCode())){
            return true;
        } else {
            throw new ServiceException(ErrorConstants.RECOVERY_CODE_NOT_MATCH);
        }
    }

    /**
     * Register user.
     *
     * @param username the username
     * @param email    the email
     * @return the user
     */
    @Transactional
    public User register(String username, String email) {
        try{
            checkIfExists(email);

            String tempPassword = RandomStringUtils.randomAlphanumeric(8);

            User user = new User(username, email, global.getPasswordEncoder().encode(tempPassword));
            user.setChangePassword(true);
            user.setEnabled(true);
            user.addRole(roleService.get(UserType.STUDENT));
            repo.save(user);

            emailSenderService.sendRegisterEmail(username,email,tempPassword);

            return user;
        }
        catch (Exception e) {
            throw new ServiceException(ErrorConstants.REGISTER_KO + email);
        }

    }

    public User get(String id) {
        Optional<User> uo = repo.findById(id);
        if (uo.isPresent())
            return uo.get();
        else
            throw new EntityNotFound(String.format(ErrorConstants.USER_MAIL_NOT_FOUND, id));
    }

    /**
     * Gets by username.
     *
     * @param username the username
     * @return the by username
     */
    public User getByUsername(String username) {
        Optional<User> user = repo.findByUsername(username);
        if(user.isEmpty()) {
            throw new EntityNotFound(String.format(ErrorConstants.USER_NOT_FOUND, username));
        }
        return user.get();
    }

    /**
     * Gets by email.
     *
     * @param email the email
     * @return the by email
     */
    public User getByEmail(String email) {
        User user = repo.findByEmail(email);
        if(user == null) {
            throw new EntityNotFound(String.format(ErrorConstants.USER_NOT_FOUND, email));
        }
        return user;
    }

    /**
     * Exists email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public Boolean existsEmail(String email) {
        return repo.existsByEmail(email);
    }

    /**
     * Add user internal user.
     *
     * @param username the username
     * @param email    the email
     * @param password the password
     * @param roles    the roles
     * @return the user
     */
    @Transactional
    public User addUserInternal(String username, String email, String password, List<UserType> roles) {
        checkIfExists(email);

        User user = new User(username, email, password);
        user.setChangePassword(false);
        user.setEnabled(true);

        for (UserType ut: roles) {
            Role role = roleService.get(ut);
            user.addRole(role);
        }
        repo.save(user);

        return user;
    }

    private void checkIfExists(String email) {
        if (repo.existsByEmail(email))
            throw new ServiceException(ErrorConstants.USER_ALREADY_EXIST + email);
    }

    /**
     * Sets last login.
     *
     * @param user the user
     */
    @Transactional
    public void setLastLogin(User user) {
        user.setLastLogin(new Date());
        repo.save(user);
    }

    /**
     * Sets current project.
     *
     * @param user    the user
     * @param project the project
     */
    @Transactional
    public void setCurrentProject(User user, Project project) {
        user.setCurrentProject(project.getId());
        repo.save(user);
    }

    /**
     * Change password.
     *
     * @param user        the user
     * @param newpassword the newpassword
     */
    @Transactional
    public void changePassword(User user, String newpassword) {
        user.setPassword(global.getPasswordEncoder().encode(newpassword));
        user.setChangePassword(false);
        if(null != user.getRecoveryCode()) user.setRecoveryCode(null);
        repo.save(user);
    }

    /**
     * Edit my user user.
     *
     * @param modifier       the modifier
     * @param user           the user
     * @param username       the username
     * @param color          the color
     * @param capitalLetters the capital letters
     * @param changePassword the change password
     * @param githubToken    the github token
     * @param enabled        the enabled
     * @return the user
     */
    @Transactional
    public User editMyUser(User modifier, User user, String username, String color,
                         String capitalLetters, Boolean changePassword,
                         String githubToken, Boolean enabled) {
        if(username != null && modifier.isUserType(UserType.ADMIN)) user.setUsername(username);
        if(color != null) user.setColor(color);
        if(capitalLetters != null) user.setCapitalLetters(capitalLetters);
        if(changePassword != null) user.setChangePassword(changePassword);
        if(enabled != null && modifier.isUserType(UserType.ADMIN)) user.setEnabled(enabled);
        if(githubToken != null && !githubToken.isEmpty()) {
            user.setGithubToken(githubToken);
            ResponseEntity<GithubInfo> githubInfo = githubService.getGithubInformation(user.getGithubInfo().getGithub_token());
            if(githubInfo.getStatusCode().is2xxSuccessful()) {
                GithubInfo responseBody = githubInfo.getBody();
                if(responseBody != null) {
                    user.setGithubName(responseBody.getLogin());
                    user.setGithubAvatar(responseBody.getAvatar_url());
                    user.setGithubHtmlUrl(responseBody.getHtml_url());
                }
            }
            else if(githubInfo.getStatusCode().is4xxClientError()) {
                user.setGithubToken(ErrorConstants.GITHUB_TOKEN_INVALID);
                user.setGithubName(null);
                user.setGithubAvatar(null);
                user.setGithubHtmlUrl(null);
            }
            else {
                user.setGithubToken(ErrorConstants.API_GITHUB_KO);
                user.setGithubName(null);
                user.setGithubAvatar(null);
                user.setGithubHtmlUrl(null);
            }
        } else {
            user.setGithubToken(null);
            user.setGithubName(null);
            user.setGithubAvatar(null);
            user.setGithubHtmlUrl(null);
        }
        repo.save(user);
        return user;
    }

    /**
     * Generate recovery code string.
     *
     * @param user the user
     * @return the string
     */
    @Transactional
    public String generateRecoveryCode(User user) {
        String code = RandomStringUtils.randomAlphanumeric(8);
        user.setRecoveryCode(global.getPasswordEncoder().encode(code));
        repo().save(user);
        return code;
    }

    /**
     * Clean recovery code.
     *
     * @param user the user
     */
    public void cleanRecoveryCode(User user) {
        user.setRecoveryCode(null);
        repo().save(user);
    }

}
