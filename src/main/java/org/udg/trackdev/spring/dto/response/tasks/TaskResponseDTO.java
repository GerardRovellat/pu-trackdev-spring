package org.udg.trackdev.spring.dto.response.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.dto.TaskDTO;
import org.udg.trackdev.spring.dto.response.sprints.SprintResponseDTO;
import org.udg.trackdev.spring.dto.response.users.UserDTO;
import org.udg.trackdev.spring.entity.TaskStatus;

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
