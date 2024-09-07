package org.udg.trackdev.spring.dto.request.courses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.utils.Constants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * The type Edit course request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditCourseRequestDTO {

    @Min(value = Constants.COURSE_MIN_START_YEAR)
    @Max(value = Constants.COURSE_MAX_START_YEAR)
    private Integer startYear;

    private Long subjectId;

    private String githubOrganization;

}
