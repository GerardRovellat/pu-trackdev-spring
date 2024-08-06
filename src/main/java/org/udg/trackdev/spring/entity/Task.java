package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.lang.NonNull;
import org.udg.trackdev.spring.utils.Constants;

import javax.persistence.*;
import java.util.*;

/**
 * The type Task.
 */
@Entity
@Table(name = "tasks")
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class Task extends BaseEntityLong {

    @NonNull
    @Column(length = Constants.MAX_TASK_NAME_LENGTH)
    private String name;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    @ManyToOne
    private User reporter;

    @Column(columnDefinition = "TEXT")
    private String description;

    private TaskType type;

    private Date createdAt;

    @ManyToOne
    private User assignee;

    private Integer estimationPoints;

    @Column(name = "`status`")
    private TaskStatus status;

    @Column(name = "`rank`")
    private Integer rank;

    @OneToMany(mappedBy = "parentTask")
    private Collection<Task> childTasks;

    @ManyToOne
    @JoinColumn(name = "parentTaskId")
    private Task parentTask;

    @Column(name = "parentTaskId", insertable = false, updatable = false)
    private Long parentTaskId;

    @ManyToMany(mappedBy = "activeTasks")
    private Collection<Sprint> activeSprints = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Comment> discussion = new HashSet<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PointsReview> pointsReviewList = new ArrayList<>();

    // -- CONSTRUCTORS

    /**
     * Instantiates a new Task.
     */
    public Task() {}

    /**
     * Instantiates a new Task.
     *
     * @param name     the name
     * @param reporter the reporter
     */
    public Task(String name, User reporter) {
        this.name = name;
        this.createdAt = new Date();
        this.reporter = reporter;
        this.status = TaskStatus.BACKLOG;
        this.estimationPoints = 0;
        this.rank = 0;
    }

    // -- GETTERS AND SETTERS

    /**
     * Gets name.
     *
     * @return the name
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type.toString();
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    @NonNull
    public void setType(TaskType type) {
        this.type = type;
    }

    /**
     * Gets created at.
     *
     * @return the created at
     */
    public Date getCreatedAt() { return createdAt; }

    /**
     * Gets reporter.
     *
     * @return the reporter
     */
    public User getReporter() { return reporter; }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() { return description; }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Gets project.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Sets project.
     *
     * @param project the project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Gets assignee.
     *
     * @return the assignee
     */
    public User getAssignee() { return assignee; }

    /**
     * Sets assignee.
     *
     * @param assignee the assignee
     */
    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public TaskStatus getStatus() { return status; }

    /**
     * Gets status text.
     *
     * @return the status text
     */
    public String getStatusText() { return status.toString(); }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(TaskStatus status) {
        checkCanMoveToStatus(status);
        this.status = status;
    }

    /**
     * Sets reporter.
     *
     * @param reporter the reporter
     */
    public void setReporter(User reporter) {
        this.reporter = reporter;
    }

    /**
     * Gets estimation points.
     *
     * @return the estimation points
     */
    public Integer getEstimationPoints() { return estimationPoints; }

    /**
     * Sets estimation points.
     *
     * @param estimation the estimation
     */
    public void setEstimationPoints(Integer estimation) {
        this.estimationPoints = estimation;
    }

    /**
     * Gets rank.
     *
     * @return the rank
     */
    public Integer getRank() { return this.rank; }

    /**
     * Sets rank.
     *
     * @param rank the rank
     */
    public void setRank(Integer rank) {
        this.rank = rank;
    }

    /**
     * Gets child tasks.
     *
     * @return the child tasks
     */
    public Collection<Task> getChildTasks() {
        return childTasks;
    }

    /**
     * Add child task.
     *
     * @param task the task
     */
    public void addChildTask(Task task) { this.childTasks.add(task); }

    /**
     * Gets parent task.
     *
     * @return the parent task
     */
    public Task getParentTask() {
        return parentTask;
    }

    /**
     * Sets parent task.
     *
     * @param parentTask the parent task
     */
    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    /**
     * Gets discussion.
     *
     * @return the discussion
     */
    public Collection<Comment> getDiscussion() {
        return discussion;
    }

    /**
     * Gets points review list.
     *
     * @return the points review list
     */
    public List<PointsReview> getPointsReviewList() {
        return pointsReviewList;
    }

    /**
     * Add points review.
     *
     * @param pointsReview the points review
     */
    public  void addPointsReview(PointsReview pointsReview) {
        this.pointsReviewList.add(pointsReview);
    }

    /**
     * Add comment.
     *
     * @param comment the comment
     */
    public void addComment(Comment comment) {
        this.discussion.add(comment);
        comment.setTask(this);
    }


    /**
     * Gets active sprints.
     *
     * @return the active sprints
     */
    public Collection<Sprint> getActiveSprints() {
        return activeSprints;
    }

    /**
     * Sets active sprints.
     *
     * @param activeSprints the active sprints
     */
    public void setActiveSprints(Collection<Sprint> activeSprints) {
        this.activeSprints = activeSprints;
    }

    private void checkCanMoveToStatus(TaskStatus status) {
        /**if(this.status == TaskStatus.BACKLOG && status != TaskStatus.TODO) {
            throw new EntityException(String.format("Cannot change status from BACKLOG to new status <%s>", status));
        }
        if(status == TaskStatus.BACKLOG) {
            throw new EntityException("Cannot set status to BACKLOG");
        }**/
    }
}
