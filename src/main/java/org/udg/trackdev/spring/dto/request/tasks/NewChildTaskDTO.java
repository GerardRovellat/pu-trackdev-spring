package org.udg.trackdev.spring.dto.request.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.utils.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewChildTaskDTO {

    @NotBlank
    @Size(min = Constants.MIN_TASK_NAME_LENGTH, max = Constants.MAX_TASK_NAME_LENGTH)
    public String name;

}
