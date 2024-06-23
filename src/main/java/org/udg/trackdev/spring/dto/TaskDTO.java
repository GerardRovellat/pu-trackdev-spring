package org.udg.trackdev.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.dto.response.SprintResponseDTO;
import org.udg.trackdev.spring.entity.TaskStatus;

import java.util.Collection;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

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

    private Collection<SprintResponseDTO> activeSprints;

}
