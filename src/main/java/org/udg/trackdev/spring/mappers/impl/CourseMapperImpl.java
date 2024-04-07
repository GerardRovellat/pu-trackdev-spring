package org.udg.trackdev.spring.mappers.impl;

import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.dto.response.CourseCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.CourseResponseDTO;
import org.udg.trackdev.spring.entity.Course;
import org.udg.trackdev.spring.mappers.CourseMapper;

@Component
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseResponseDTO toCourseResponseDTO(Course course) {
        return CourseResponseDTO.builder()
                .startYear(course.getStartYear())
                .githubOrganization(course.getGithubOrganization())
                .build();
    }

    @Override
    public CourseCompleteResponseDTO toCourseCompleteResponseDTO(Course course) {
        return CourseCompleteResponseDTO.builder()
                .startYear(course.getStartYear())
                .githubOrganization(course.getGithubOrganization())
                .subject(course.getSubject())
                .build();
    }


}
