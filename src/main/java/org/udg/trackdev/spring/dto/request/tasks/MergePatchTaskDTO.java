package org.udg.trackdev.spring.dto.request.tasks;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.udg.trackdev.spring.entity.Comment;
import org.udg.trackdev.spring.entity.PointsReview;
import org.udg.trackdev.spring.entity.TaskStatus;

import java.util.Collection;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MergePatchTaskDTO {

    /**
     * The Name.
     */
    private Optional<String> name;

    /**
     * The Description.
     */
    private Optional<String> description;

    /**
     * The Assignee.
     */
    private Optional<String> assignee;

    /**
     * The Estimation points.
     */
    private Optional<Integer> estimationPoints;

    /**
     * The Status.
     */
    private Optional<TaskStatus> status;

    /**
     * The Rank.
     */
    private Optional<Integer> rank;

    /**
     * The Reporter.
     */
    private Optional<String> reporter;

    /**
     * The Active sprints.
     */
    private Optional<Collection<Long>> activeSprints;

    /**
     * The Comment.
     */
    private Optional<Comment> comment;

    /**
     * The Points review.
     */
    private Optional<PointsReview> pointsReview;

}
