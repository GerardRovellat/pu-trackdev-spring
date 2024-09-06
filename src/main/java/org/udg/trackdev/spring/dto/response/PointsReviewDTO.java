package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointsReviewDTO {

    private Integer points;

    private String comment;

    private UserDTO user;

}
