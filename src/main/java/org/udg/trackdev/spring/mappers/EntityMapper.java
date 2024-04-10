package org.udg.trackdev.spring.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.udg.trackdev.spring.dto.response.*;
import org.udg.trackdev.spring.entity.Course;
import org.udg.trackdev.spring.entity.Sprint;
import org.udg.trackdev.spring.entity.Subject;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    SubjectResponseDTO toSubjectEntityToDTO(Subject subject);

    @Mapping(target = "courses", source = "courses")
    SubjectCompleteResponseDTO toSubjectEntityToCompleteDTO(Subject subject);

    CourseResponseDTO toCourseEntityToDTO(Course course);

    CourseCompleteResponseDTO toCourseEntityToCompleteDTO(Course course);

    SprintResponseDTO toSprintEntityToDTO(Sprint sprint);

}
