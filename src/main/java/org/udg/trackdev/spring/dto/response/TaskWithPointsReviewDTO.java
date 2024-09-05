package org.udg.trackdev.spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskWithPointsReviewDTO {


    /**
     * The Task.
     */
    public TaskResponseDTO task;

    /**
     * The Points review.
     */
    public List<PointsReviewDTO> pointsReview;

}
