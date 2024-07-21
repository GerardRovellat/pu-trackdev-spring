package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

/**
 * The type Points review.
 */
@Entity
@Table(name = "points_reviews")
public class PointsReview extends BaseEntityLong {


    @Min(0)
    private Integer points;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Instantiates a new Points review.
     */
    public PointsReview() {}

    /**
     * Instantiates a new Points review.
     *
     * @param points  the points
     * @param comment the comment
     * @param task    the task
     * @param user    the user
     */
    public PointsReview(Integer points, String comment, Task task, User user) {
        this.points = points;
        this.comment = comment;
        this.task = task;
        this.user = user;
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    @JsonView(EntityLevelViews.Basic.class)
    public Integer getPoints() { return points; }

    /**
     * Sets points.
     *
     * @param points the points
     */
    public void setPoints(Integer points) { this.points = points; }

    /**
     * Gets comment.
     *
     * @return the comment
     */
    @JsonView(EntityLevelViews.Basic.class)
    public String getComment() { return comment; }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) { this.comment = comment; }

    /**
     * Gets task.
     *
     * @return the task
     */
    public Task getTask() { return task; }

    /**
     * Sets task.
     *
     * @param task the task
     */
    public void setTask(Task task) { this.task = task; }

    /**
     * Gets user.
     *
     * @return the user
     */
    @JsonView(EntityLevelViews.Basic.class)
    public User getUser() { return user; }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) { this.user = user; }
}
