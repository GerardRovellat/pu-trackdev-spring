package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.entity.enums.TaskStatus;

import java.util.Collection;
import java.util.Date;

/**
 * The type Task response dto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

    private String name;

    private String type;

    private Date createdAt;

    private String description;

    private UserDTO reporter;

    private UserDTO assignee;

    private TaskStatus status;

    private String statusText;

    private Integer estimationPoints;

    private Integer rank;

    private Collection<TaskDTO> childTasks;

    private Collection<SprintResponseDTO> activeSprints;

}
