package org.udg.trackdev.spring.dto.request.subjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.udg.trackdev.spring.entity.Course;
import org.udg.trackdev.spring.utils.Constants;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * The type Create course request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCourseRequestDTO {

    @Min(value = Constants.COURSE_MIN_START_YEAR, message = ErrorConstants.INVALID_COURSE_START_YEAR)
    @Max(value = Constants.COURSE_MAX_START_YEAR, message = ErrorConstants.INVALID_COURSE_START_YEAR)
    private Integer startYear;

    private String githubOrganization;

}
