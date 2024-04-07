package org.udg.trackdev.spring.mappers.impl;

import org.springframework.stereotype.Component;
import org.udg.trackdev.spring.dto.response.SprintResponseDTO;
import org.udg.trackdev.spring.entity.Sprint;
import org.udg.trackdev.spring.mappers.SprintMapper;

@Component
public class SprintMapperImpl implements SprintMapper {

    @Override
    public SprintResponseDTO toSprintResponseDTO(Sprint sprint) {
        return SprintResponseDTO.builder()
                .name(sprint.getName())
                .startDate(sprint.getStartDate())
                .endDate(sprint.getEndDate())
                .status(sprint.getStatus().name())
                .statusText(sprint.getStatus().toString())
                .build();
    }

}
