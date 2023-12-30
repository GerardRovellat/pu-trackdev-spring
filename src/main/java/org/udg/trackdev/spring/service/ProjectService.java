package org.udg.trackdev.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.trackdev.spring.controller.exceptions.ServiceException;
import org.udg.trackdev.spring.entity.*;
import org.udg.trackdev.spring.repository.GroupRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class ProjectService extends BaseServiceLong<Project, GroupRepository> {

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

    @Autowired
    SprintService sprintService;

    @Autowired
    AccessChecker accessChecker;

    @Transactional
    public Project createProject(String name, Collection<String> emails, Long courseId,
                                 String loggedInUserId) {
        Course course = courseService.get(courseId);
        accessChecker.checkCanManageCourse(course, loggedInUserId);
        Project project = new Project(name);
        course.addProject(project);
        project.setCourse(course);

        if(emails != null && !emails.isEmpty()) {
            addMembers(course, project, emails);
        }
        repo.save(project);
        return project;
    }

    @Transactional
    public Project editProject(Long projectId, String name, Collection<String> mails, Long courseId,
                               String loggedInUserId) {
        Project project = get(projectId);
        accessChecker.checkCanManageProject(project, loggedInUserId);
        if(name != null) {
            project.setName(name);
        }
        if(mails != null) {
            if(mails.isEmpty() && !project.getMembers().isEmpty()) {
                throw new ServiceException("Cannot remove all members of a project");
            }
            editMembers(mails, project);
        }
        if(courseId != null) {
            Course course = courseService.get(courseId);
            project.setCourse(course);
        }
        repo.save(project);
        
        return project;
    }

    @Transactional
    public Project createProjectTask(Project project, String name, User reporter){
        Task task = new Task(name, reporter);
        project.addTask(task);
        task.setProject(project);
        repo.save(project);
        return project;
    }

    @Transactional
    public void deleteProject(Long groupId, String userId) {
        Project project = get(groupId);
        accessChecker.checkCanManageProject(project, userId);
        repo.delete(project);
    }

    public Collection<Sprint> getProjectSprints(Project project) {
        return project.getSprints();
    }

    public Collection<Task> getProjectTasks(Project project) {
        return project.getTasks();
    }

    public void createSprint(Project project, String name, Date startDate, Date endDate, String userId) {
        accessChecker.checkCanViewProject(project, userId);
        Sprint sprint = sprintService.create(project, name, startDate, endDate, userId);
        project.addSprint(sprint);
        repo.save(project);
    }

    private void addMembers(Course course, Project project, Collection<String> usernames) {
        for(String username: usernames) {
            User user = userService.getByEmail(username);
            addMember(course, project, user);
        }
    }


    private void addMember(Course course, Project project, User user) {
        project.addMember(user);
        user.addToGroup(project);
    }

    private void editMembers(Collection<String> mails, Project project) {
        for(String mail: mails) {
            User user = userService.getByEmail(mail);
            if(!project.isMember(user)) {
                addMember(project.getCourse(), project, user);
            }
        }
        List<User> toRemove = new ArrayList<>();
        for(User user: project.getMembers()) {
            if(!mails.contains(user.getEmail())) {
                toRemove.add(user);
            }
        }
        for(User user: toRemove) {
            project.removeMember(user);
            user.removeFromGroup(project);
        }
    }

}
