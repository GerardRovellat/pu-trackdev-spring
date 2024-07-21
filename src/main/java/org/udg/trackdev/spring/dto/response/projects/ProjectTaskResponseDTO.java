package org.udg.trackdev.spring.dto.response.projects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.dto.response.tasks.TaskResponseDTO;

import java.util.Collection;

/**
 * The type Project task response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectTaskResponseDTO {

    private Collection<TaskResponseDTO> tasks;

    private Long projectId;

}
