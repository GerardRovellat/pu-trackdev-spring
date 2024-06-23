package org.udg.trackdev.spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.entity.Sprint;

import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSprintRequestDTO {

    @Size(min = Sprint.MIN_NAME_LENGTH, max = Sprint.NAME_LENGTH)
    private String name;

    private Date startDate;

    private Date endDate;

}
