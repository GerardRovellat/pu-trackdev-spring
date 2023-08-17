package org.udg.trackdev.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.trackdev.spring.entity.*;
import org.udg.trackdev.spring.repository.CourseRepository;

import java.util.Collection;

@Service
public class CourseService extends BaseServiceLong<Courses, CourseRepository> {

    @Autowired
    SubjectService subjectService;

    @Autowired
    InviteCourseBuilder courseInviteBuilder;

    @Autowired
    UserService userService;

    @Autowired
    AccessChecker accessChecker;

    @Transactional
    public Courses createCourse(Long courseId, Integer startYear, String loggedInUserId) {
        Subject subject = subjectService.getSubject(courseId);
        accessChecker.checkCanManageCourse(subject, loggedInUserId);
        Courses courses = new Courses(startYear);
        courses.setSubject(subject);
        subject.addCourseYear(courses);
        return courses;
    }

    public void deleteCourse(Long yearId, String loggedInUserId) {
        Courses courses = get(yearId);
        accessChecker.checkCanManageCourseYear(courses, loggedInUserId);
        repo.delete(courses);
    }

    @Transactional
    public Invite createInvite(String email, Long yearId, String ownerId) {
        Courses courses = get(yearId);
        Invite invite = courseInviteBuilder.Build(email, ownerId, courses);
        return invite;
    }

    @Transactional
    public void removeStudent(Long yearId, String username, String loggedInUserId) {
        Courses courses = get(yearId);
        accessChecker.checkCanManageCourseYear(courses, loggedInUserId);
        User user = userService.getByUsername(username);
        for(Project project : courses.getProjects()) {
            if(project.isMember(user)) {
                project.removeMember(user);
                user.removeFromGroup(project);
            }
        }
        courses.removeStudent(user);
        user.removeFromCourseYear(courses);
    }

    public Collection<Courses> getAll(){
        return repo.findAll();
    }
}
