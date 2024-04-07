package org.udg.trackdev.spring.facade;

import org.udg.trackdev.spring.dto.response.CourseCompleteResponseDTO;

import java.util.List;

public interface CourseFacade {

    List<CourseCompleteResponseDTO> getAllCourses();

}
