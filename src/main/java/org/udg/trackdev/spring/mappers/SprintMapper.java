package org.udg.trackdev.spring.mappers;

import org.udg.trackdev.spring.dto.response.SprintResponseDTO;
import org.udg.trackdev.spring.entity.Sprint;

public interface SprintMapper {

    SprintResponseDTO toSprintResponseDTO(Sprint sprint);

}
