package org.udg.trackdev.spring.dto.request.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.utils.Constants;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * The type Create sprint request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSprintRequestDTO {

    @Size(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH)
    private String name;

    private Date startDate;

    private Date endDate;

}
