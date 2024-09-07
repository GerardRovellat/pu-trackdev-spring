package org.udg.trackdev.spring.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.udg.trackdev.spring.dto.response.CourseDTO;
import org.udg.trackdev.spring.dto.response.SelfResponseDTO;
import org.udg.trackdev.spring.dto.response.GithubWithTokenDTO;
import org.udg.trackdev.spring.dto.response.SubjectResponseDTO;
import org.udg.trackdev.spring.dto.response.TaskChangeDTO;
import org.udg.trackdev.spring.dto.response.UserLoginDTO;
import org.udg.trackdev.spring.dto.response.CourseCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectSprintsResponseDTO;
import org.udg.trackdev.spring.dto.response.ProjectWithUserResponseDTO;
import org.udg.trackdev.spring.dto.response.SprintResponseDTO;
import org.udg.trackdev.spring.dto.response.SubjectCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.CommentDTO;
import org.udg.trackdev.spring.dto.response.PointsReviewDTO;
import org.udg.trackdev.spring.dto.response.TaskResponseDTO;
import org.udg.trackdev.spring.dto.response.UserDTO;
import org.udg.trackdev.spring.dto.response.UserWithoutProjectMembersResponseDTO;
import org.udg.trackdev.spring.entity.*;
import org.udg.trackdev.spring.entity.changes.taskchanges.*;
import org.udg.trackdev.spring.service.Global;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * The interface Entity mapper.
 */
@Mapper(componentModel = "spring")
public interface EntityMapper {

    /**
     * To subject entity to dto subject response dto.
     *
     * @param subject the subject
     * @return the subject response dto
     */
    SubjectResponseDTO toSubjectEntityToDTO(Subject subject);

    /**
     * Subject entity to complete dto subject complete response dto.
     *
     * @param subject the subject
     * @return the subject complete response dto
     */
    @Mapping(target = "courses", source = "courses")
    SubjectCompleteResponseDTO subjectEntityToCompleteDTO(Subject subject);

    /**
     * Course entity to dto course response dto.
     *
     * @param course the course
     * @return the course response dto
     */
    CourseDTO courseEntityToDTO(Course course);

    /**
     * Course entity to complete dto course complete response dto.
     *
     * @param course the course
     * @return the course complete response dto
     */
    CourseCompleteResponseDTO courseEntityToCompleteDTO(Course course);

    /**
     * Sprint entity to dto sprint response dto.
     *
     * @param sprint the sprint
     * @return the sprint response dto
     */
    SprintResponseDTO sprintEntityToDTO(Sprint sprint);

    /**
     * User entity to dto user dto.
     *
     * @param user the user
     * @return the user dto
     */
    @Mapping(target = "roles", qualifiedByName = "serializeRoles")
    UserDTO userEntityToDTO(User user);

    @Mapping(target = "roles", qualifiedByName = "serializeRoles")
    @Mapping(target = "lastLogin", qualifiedByName = "serializeDate")
    UserLoginDTO userEntityToUserLoginDTO(User user);

    @Mapping(target = "roles", qualifiedByName = "serializeRoles")
    SelfResponseDTO userEntityToSelfDTO(User user);

    /**
     * Github entity to dto github dto.
     *
     * @param githubInfo the github info
     * @return the github dto
     */
    GithubWithTokenDTO githubEntityToDTO(GithubInfo githubInfo);

    /**
     * Project entity to dto project response dto.
     *
     * @param project the project
     * @return the project response dto
     */
    @Mapping(target = "course", source = "course")
    ProjectResponseDTO projectEntityToDTO(Project project);

    /**
     * Project entity to project with user dto project with user response dto.
     *
     * @param project the project
     * @return the project with user response dto
     */
    @Mapping(target = "members", source = "members")
    @Mapping(target = "course", source = "course")
    ProjectWithUserResponseDTO projectEntityToProjectWithUserDTO(Project project);

    /**
     * Project entity to project complete dto project complete response dto.
     *
     * @param project the project
     * @return the project complete response dto
     */
    @Mapping(target = "members", source = "members")
    @Mapping(target = "sprints", source = "sprints")
    @Mapping(target = "tasks", source = "tasks")
    @Mapping(target = "course", source = "course")
    ProjectCompleteResponseDTO projectEntityToProjectCompleteDTO(Project project);

    /**
     * Task entity to dto task response dto.
     *
     * @param task the task
     * @return the task response dto
     */
    @Mapping(target = "childTasks", source = "childTasks")
    @Mapping(target = "activeSprints", source = "activeSprints")
    TaskResponseDTO taskEntityToDTO(Task task);

    /**
     * Sprint entity to project sprints dto project sprints response dto.
     *
     * @param sprint the sprint
     * @return the project sprints response dto
     */
    @Mapping(target = "value", source = "name")
    @Mapping(target = "label", source = "name")
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "serializeDate")
    @Mapping(target = "endDate", source = "endDate", qualifiedByName = "serializeDate")
    @Mapping(target = "status", source = "status")
    ProjectSprintsResponseDTO sprintEntityToProjectSprintsDTO(Sprint sprint);

    /**
     * User entity to user without project members user without project members response dto.
     *
     * @param user the user
     * @return the user without project members response dto
     */
    @Mapping(source = "roles", target = "roles", qualifiedByName = "serializeRoles")
    UserWithoutProjectMembersResponseDTO userEntityToUserWithoutProjectMembers(User user);

    PointsReviewDTO pointsEntityToDTO(PointsReview pointsReview);

    CommentDTO commentEntityToDTO(Comment comment);

    @Mapping(target = "changedAt", qualifiedByName = "serializeDate")
    TaskChangeDTO taskActiveSprintsChangeEntityToDTO(TaskActiveSprintsChange taskActiveSprintsChange);

    @Mapping(target = "changedAt", qualifiedByName = "serializeDate")
    TaskChangeDTO taskAssigneeChangeEntityToDTO(TaskAssigneeChange taskAssigneeChange);

    @Mapping(target = "changedAt", qualifiedByName = "serializeDate")
    TaskChangeDTO taskStatusChangeEntityToDTO(TaskStatusChange taskStatusChange);

    @Mapping(target = "changedAt", qualifiedByName = "serializeDate")
    TaskChangeDTO taskRankChangeEntityToDTO(TaskRankChange taskRankChange);

    @Mapping(target = "changedAt", qualifiedByName = "serializeDate")
    TaskChangeDTO taskEstimationPointsChangeEntityToDTO(TaskEstimationPointsChange taskPointsChange);

    @Mapping(target = "changedAt", qualifiedByName = "serializeDate")
    TaskChangeDTO taskNameChangeEntityToDTO(TaskNameChange taskNameChange);

    /**
     * Serialize roles list.
     *
     * @param roles the roles
     * @return the list
     */
    @Named("serializeRoles")
    default List<String> serializeRoles(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(role -> role.getUserType().toString())
                .collect(Collectors.toList());
    }

    /**
     * Serialize date string.
     *
     * @param date the date
     * @return the string
     */
    @Named("serializeDate")
    default String serializeDate(Date date) {
        if (date == null) {
            return null;
        }
        return Global.dateFormat.format(date);
    }

    default TaskChangeDTO taskChangeInheritEntityToDTO(TaskChange entity) {
        if (entity instanceof TaskEstimationPointsChange) {
            return taskEstimationPointsChangeEntityToDTO((TaskEstimationPointsChange) entity);
        } else if (entity instanceof TaskRankChange) {
            return taskRankChangeEntityToDTO((TaskRankChange) entity);
        } else if (entity instanceof TaskStatusChange) {
            return taskStatusChangeEntityToDTO((TaskStatusChange) entity);
        } else if (entity instanceof TaskAssigneeChange) {
            return taskAssigneeChangeEntityToDTO((TaskAssigneeChange) entity);
        } else if (entity instanceof TaskActiveSprintsChange) {
            return taskActiveSprintsChangeEntityToDTO((TaskActiveSprintsChange) entity);
        } else if (entity instanceof TaskNameChange) {
            return taskNameChangeEntityToDTO((TaskNameChange) entity);
        } else {
            return null;
        }
    }

}
