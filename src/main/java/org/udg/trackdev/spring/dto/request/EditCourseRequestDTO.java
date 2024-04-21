package org.udg.trackdev.spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.entity.Course;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditCourseRequestDTO {

    @Min(value = Course.MIN_START_YEAR)
    @Max(value = Course.MAX_START_YEAR)
    private Integer startYear;

    private Long subjectId;

    private String githubOrganization;

}
