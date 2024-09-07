package org.udg.trackdev.spring.dto.request.courses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.utils.Constants;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * The type Create project request dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectRequestDTO {

    @NotBlank
    @Size(min = Constants.MIN_NAME_LENGTH, max = Constants.MAX_NAME_LENGTH)
    private String name;

    private Collection<String> members;

}
