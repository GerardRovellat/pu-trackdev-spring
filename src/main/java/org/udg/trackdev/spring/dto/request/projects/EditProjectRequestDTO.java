package org.udg.trackdev.spring.dto.request.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.entity.Project;
import org.udg.trackdev.spring.utils.ErrorConstants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * The type Edit project request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EditProjectRequestDTO {

    @Size(min = Project.MIN_NAME_LENGTH, max = Project.NAME_LENGTH, message = ErrorConstants.INVALID_PRJ_NAME_LENGTH)
    private String name;

    private Collection<String> members;

    private Long courseId;

    @Min(value = Project.MIN_QUALIFICATION, message = ErrorConstants.INVALID_PRJ_QUALIFICATION)
    @Max(value = Project.MAX_QUALIFICATION, message = ErrorConstants.INVALID_PRJ_QUALIFICATION)
    private Double qualification;

}
