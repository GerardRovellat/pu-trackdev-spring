package org.udg.trackdev.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.trackdev.spring.entity.Course;
import org.udg.trackdev.spring.entity.Subject;
import org.udg.trackdev.spring.repository.CourseRepository;

import java.util.List;

/**
 * The type Course service.
 */
@Service
public class CourseService extends BaseServiceLong<Course, CourseRepository> {

    /**
     * The Subject service.
     */
    @Autowired
    SubjectService subjectService;

    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    /**
     * The Access checker.
     */
    @Autowired
    AccessChecker accessChecker;

    public List<Course> getAll(){
        return repo.findAll();
    }

    /**
     * Create course course.
     *
     * @param subjectId      the subject id
     * @param startYear      the start year
     * @param organization   the organization
     * @param loggedInUserId the logged in user id
     * @return the course
     */
    @Transactional
    public Course createCourse(Long subjectId, Integer startYear, String organization, String loggedInUserId) {
        Subject subject = subjectService.getSubject(subjectId);
        accessChecker.checkCanManageSubject(subject, loggedInUserId);
        Course course = new Course(startYear);
        course.setSubject(subject);
        course.setGithubOrganization(organization);
        subject.addCourse(course);
        return course;
    }

    /**
     * Edit course course.
     *
     * @param courseId     the course id
     * @param startYear    the start year
     * @param subjectId    the subject id
     * @param organization the organization
     * @param userId       the user id
     * @return the course
     */
    @Transactional
    public Course editCourse(Long courseId, Integer startYear, Long subjectId, String organization, String userId){
        Course course = get(courseId);
        accessChecker.checkCanManageCourse(course, userId);
        course.setStartYear(startYear);
        Subject subject = subjectService.getSubject(subjectId);
        course.setSubject(subject);
        course.setGithubOrganization(organization);
        repo.save(course);
        return course;
    }

    /**
     * Delete course.
     *
     * @param courseId       the course id
     * @param loggedInUserId the logged in user id
     */
    public void deleteCourse(Long courseId, String loggedInUserId) {
        Course course = get(courseId);
        accessChecker.checkCanManageCourse(course, loggedInUserId);
        repo.delete(course);
    }

}
