package org.udg.trackdev.spring.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.dto.response.CourseCompleteResponseDTO;
import org.udg.trackdev.spring.facade.CourseFacade;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CourseFacadeImpl implements CourseFacade {

    @Override
    public List<CourseCompleteResponseDTO> getAllCourses() {
        return null;
    }

}
