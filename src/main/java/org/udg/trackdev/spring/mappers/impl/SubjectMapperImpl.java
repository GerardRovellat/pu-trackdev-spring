package org.udg.trackdev.spring.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.dto.response.SubjectResponseDTO;
import org.udg.trackdev.spring.entity.Subject;
import org.udg.trackdev.spring.mappers.CourseMapper;
import org.udg.trackdev.spring.mappers.SubjectMapper;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SubjectMapperImpl implements SubjectMapper {

    private final CourseMapper courseMapper;

    @Override
    public SubjectResponseDTO toSubjectResponseDTO(Subject subject) {
        return SubjectResponseDTO.builder()
                .name(subject.getName())
                .ownerId(subject.getOwnerId())
                .acronym(subject.getAcronym())
                .courses(subject.getCourses().stream().map(courseMapper::toCourseResponseDTO).collect(Collectors.toList()))
                .build();
    }
}
