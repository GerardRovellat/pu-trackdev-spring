package org.udg.trackdev.spring.entity;

import lombok.*;
import org.udg.trackdev.spring.utils.Constants;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

/**
 * The type Points review.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "points_reviews")
public class PointsReview extends BaseEntityLong {


    @Min(Constants.MIN_POINTS)
    private Integer points;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
