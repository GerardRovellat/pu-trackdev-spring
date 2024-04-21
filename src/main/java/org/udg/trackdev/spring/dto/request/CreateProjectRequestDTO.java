package org.udg.trackdev.spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.entity.Project;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectRequestDTO {

    @NotBlank
    @Size(min = Project.MIN_NAME_LENGTH, max = Project.NAME_LENGTH)
    private String name;

    private Collection<String> members;

}
