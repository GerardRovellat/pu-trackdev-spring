package org.udg.trackdev.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.configuration.UserType;
import org.udg.trackdev.spring.controller.exceptions.ServiceException;
import org.udg.trackdev.spring.entity.Course;
import org.udg.trackdev.spring.entity.Project;
import org.udg.trackdev.spring.entity.Subject;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.utils.ErrorConstants;

/**
 * The type Access checker.
 */
/*
 * Centralized service to control if current principal
 * has access to a concrete entity. Access can be
 * read and manage (includes edit and delete)
 *
 * Note: methods starting with check throw exceptions.
 * otherwise they return boolean.
 */
@Component
public class AccessChecker {

    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    // SUBJECTS

    /**
     * Check can create subject.
     *
     * @param user the user
     */
    public void checkCanCreateSubject(User user) {
        boolean isProfessor = user.isUserType(UserType.ADMIN);
        if(!isProfessor) {
            throw new ServiceException(ErrorConstants.UNAUTHORIZED);
        }
    }

    /**
     * Check can manage subject.
     *
     * @param subject the subject
     * @param userId  the user id
     */
    public void checkCanManageSubject(Subject subject, String userId) {
        if(!isSubjectOwner(subject, userId)) {
            throw new ServiceException(ErrorConstants.UNAUTHORIZED);
        }
    }

    /**
     * Check can view subject.
     *
     * @param subject the subject
     * @param userId  the user id
     */
    public void checkCanViewSubject(Subject subject, String userId) {
        if(isSubjectOwner(subject, userId)) {
            return;
        }
        throw new ServiceException(ErrorConstants.UNAUTHORIZED);
    }

    // COURSES

    /**
     * Check can view all courses.
     *
     * @param userId the user id
     */
    public void checkCanViewAllCourses(String userId) {
        if(userService.get(userId).isUserType(UserType.ADMIN)) {
            return;
        }
        throw new ServiceException(ErrorConstants.UNAUTHORIZED);
    }

    /**
     * Check can manage course.
     *
     * @param course the course
     * @param userId the user id
     */
    public void checkCanManageCourse(Course course, String userId) {
        checkCanManageSubject(course.getSubject(), userId);
    }

    /**
     * Check can view course.
     *
     * @param course the course
     * @param userId the user id
     */
    public void checkCanViewCourse(Course course, String userId) {
        if(isSubjectOwner(course.getSubject(), userId)) {
            return;
        }
        throw new ServiceException(ErrorConstants.UNAUTHORIZED);
    }


    /**
     * Can view course all projects boolean.
     *
     * @param course the course
     * @param userId the user id
     * @return the boolean
     */
    public boolean canViewCourseAllProjects(Course course, String userId) {
        if(isSubjectOwner(course.getSubject(), userId)) {
            return true;
        }
        return false;
    }

    // PROJECTS

    /**
     * Check can manage project.
     *
     * @param project the project
     * @param userId  the user id
     */
    public void checkCanManageProject(Project project, String userId) {
        checkCanManageCourse(project.getCourse(), userId);
    }

    /**
     * Check can view project.
     *
     * @param project the project
     * @param userId  the user id
     */
    public void checkCanViewProject(Project project, String userId) {
        if(project.isMember(userId)) {
            return;
        }
        Subject subject = project.getCourse().getSubject();
        if(isSubjectOwner(subject, userId)) {
            return;
        }
        if (userService.get(userId).isUserType(UserType.ADMIN)) {
            return;
        }
        throw new ServiceException(ErrorConstants.UNAUTHORIZED);
    }

    /**
     * Check can view all tasks.
     *
     * @param userId the user id
     */
    public void checkCanViewAllTasks(String userId) {
        if(userService.get(userId).isUserType(UserType.ADMIN)) {
            return;
        }
        throw new ServiceException(ErrorConstants.UNAUTHORIZED);
    }

    /**
     * Check can view all projects boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean checkCanViewAllProjects(String userId) {
        if(userService.get(userId).isUserType(UserType.ADMIN)) {
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isSubjectOwner(Subject subject, String userId) {
        return subject.getOwnerId().equals(userId);
    }

    /**
     * Is user admin boolean.
     *
     * @param user the user
     * @return the boolean
     */
    public boolean isUserAdmin(User user) {
        return user.isUserType(UserType.ADMIN);
    }

}
