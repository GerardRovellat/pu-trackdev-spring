package org.udg.trackdev.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.trackdev.spring.controller.exceptions.ServiceException;
import org.udg.trackdev.spring.dto.response.projects.ProjectRankDTO;
import org.udg.trackdev.spring.entity.*;
import org.udg.trackdev.spring.repository.GroupRepository;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Project service.
 */
@Service
public class ProjectService extends BaseServiceLong<Project, GroupRepository> {

    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    /**
     * The Course service.
     */
    @Autowired
    CourseService courseService;

    /**
     * The Sprint service.
     */
    @Autowired
    SprintService sprintService;

    /**
     * The Access checker.
     */
    @Autowired
    AccessChecker accessChecker;

    /**
     * Create project project.
     *
     * @param name           the name
     * @param emails         the emails
     * @param courseId       the course id
     * @param loggedInUserId the logged in user id
     * @return the project
     */
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

    /**
     * Edit project project.
     *
     * @param projectId      the project id
     * @param name           the name
     * @param mails          the mails
     * @param courseId       the course id
     * @param qualification  the qualification
     * @param loggedInUserId the logged in user id
     * @return the project
     */
    @Transactional
    public Project editProject(Long projectId, String name, Collection<String> mails, Long courseId, Double qualification
                               , String loggedInUserId) {
        Project project = get(projectId);
        accessChecker.checkCanManageProject(project, loggedInUserId);
        if(name != null) {
            project.setName(name);
        }
        if(mails != null) {
            if(mails.isEmpty() && !project.getMembers().isEmpty()) {
                throw new ServiceException(ErrorConstants.PRJ_WITHOUT_MEMBERS);
            }
            editMembers(mails, project);
        }
        if(courseId != null) {
            Course course = courseService.get(courseId);
            project.setCourse(course);
        }
        project.setQualification(qualification);
        repo.save(project);
        
        return project;
    }

    /**
     * Create project task project.
     *
     * @param project  the project
     * @param name     the name
     * @param reporter the reporter
     * @return the project
     */
    @Transactional
    public Project createProjectTask(Project project, String name, User reporter){
        Task task = new Task(name, reporter);
        project.addTask(task);
        task.setProject(project);
        repo.save(project);
        return project;
    }

    /**
     * Delete project.
     *
     * @param groupId the group id
     * @param userId  the user id
     */
    @Transactional
    public void deleteProject(Long groupId, String userId) {
        Project project = get(groupId);
        accessChecker.checkCanManageProject(project, userId);
        repo.delete(project);
    }

    /**
     * Gets project sprints.
     *
     * @param project the project
     * @return the project sprints
     */
    public Collection<Sprint> getProjectSprints(Project project) {
        return project.getSprints();
    }

    /**
     * Gets project tasks.
     *
     * @param project the project
     * @return the project tasks
     */
    public Collection<Task> getProjectTasks(Project project) {
        return project.getTasks();
    }

    /**
     * Create sprint.
     *
     * @param project   the project
     * @param name      the name
     * @param startDate the start date
     * @param endDate   the end date
     * @param userId    the user id
     */
    public void createSprint(Project project, String name, Date startDate, Date endDate, String userId) {
        accessChecker.checkCanViewProject(project, userId);
        Sprint sprint = sprintService.create(project, name, startDate, endDate, userId);
        project.addSprint(sprint);
        repo.save(project);
    }

    /**
     * Gets project ranks.
     *
     * @param project the project
     * @return the project ranks
     */
    public Map<String, ProjectRankDTO> getProjectRanks(Project project) {
        if(project.getQualification() != null){
            Map<String, ProjectRankDTO> ranks = new HashMap<>();
            Map<User, Integer> points = project.getTasks().stream()
                    .filter(task -> task.getAssignee() != null)
                    .collect(Collectors.groupingBy(Task::getAssignee, Collectors.summingInt(Task::getEstimationPoints)));
            Integer maxPoints = points.values().stream().max(Integer::compareTo).orElse(0);
            for(User user: points.keySet()) {
                ProjectRankDTO rank = ProjectRankDTO.builder()
                        .name(user.getUsername())
                        .acronym(user.getCapitalLetters())
                        .color(user.getColor())
                        .qualification(String.valueOf(BigDecimal.valueOf(
                                                points.get(user).doubleValue() * project.getQualification() / maxPoints.doubleValue())
                                        .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()))
                        .build();
                ranks.put(user.getEmail(),rank);
            }
            return ranks;
        }
        else{
            throw new ServiceException(ErrorConstants.PRJ_WITHOUT_QUALIFICATION);
        }
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
