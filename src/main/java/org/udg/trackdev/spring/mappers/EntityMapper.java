package org.udg.trackdev.spring.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.udg.trackdev.spring.dto.GithubDTO;
import org.udg.trackdev.spring.dto.UserDTO;
import org.udg.trackdev.spring.dto.response.*;
import org.udg.trackdev.spring.entity.*;
import org.udg.trackdev.spring.service.Global;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public interface EntityMapper {

    SubjectResponseDTO toSubjectEntityToDTO(Subject subject);

    @Mapping(target = "courses", source = "courses")
    SubjectCompleteResponseDTO subjectEntityToCompleteDTO(Subject subject);

    CourseResponseDTO courseEntityToDTO(Course course);

    CourseCompleteResponseDTO courseEntityToCompleteDTO(Course course);

    SprintResponseDTO sprintEntityToDTO(Sprint sprint);

    @Mapping(target = "roles", qualifiedByName = "serializeRoles")
    @Mapping(target = "lastLogin", qualifiedByName = "serializeDate")
    UserDTO userEntityToDTO(User user);

    GithubDTO githubEntityToDTO(GithubInfo githubInfo);

    @Mapping(target = "course", source = "course")
    ProjectResponseDTO projectEntityToDTO(Project project);

    @Mapping(target = "members", source = "members")
    @Mapping(target = "course", source = "course")
    ProjectWithUserResponseDTO projectEntityToProjectWithUserDTO(Project project);

    @Mapping(target = "members", source = "members")
    @Mapping(target = "sprints", source = "sprints")
    @Mapping(target = "tasks", source = "tasks")
    @Mapping(target = "course", source = "course")
    ProjectCompleteResponseDTO projectEntityToProjectCompleteDTO(Project project);

    @Mapping(target = "childTasks", source = "childTasks")
    @Mapping(target = "activeSprints", source = "activeSprints")
    TaskResponseDTO taskEntityToDTO(Task task);

    @Mapping(target = "value", source = "name")
    @Mapping(target = "label", source = "name")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "status", source = "status")
    ProjectSprintsResponseDTO sprintEntityToProjectSprintsDTO(Sprint sprint);

    UserWithoutProjectMembersResponseDTO userEntityToUserWithoutProjectMembers(User user);

    @Named("serializeRoles")
    default List<String> serializeRoles(Set<Role> roles) {
        if (roles == null) {
            return null;
        }
        return roles.stream()
                .map(role -> role.getUserType().toString())
                .collect(Collectors.toList());
    }

    @Named("serializeDate")
    default String serializeDate(Date date) {
        if (date == null) {
            return null;
        }
        return Global.dateFormat.format(date);
    }

}
