package org.udg.trackdev.spring.mappers;

import org.udg.trackdev.spring.dto.response.CourseCompleteResponseDTO;
import org.udg.trackdev.spring.dto.response.CourseResponseDTO;
import org.udg.trackdev.spring.entity.Course;

public interface CourseMapper{

    CourseResponseDTO toCourseResponseDTO(Course course);

    CourseCompleteResponseDTO toCourseCompleteResponseDTO(Course course);

}
