package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.springframework.lang.NonNull;
import org.udg.trackdev.spring.entity.enums.TaskStatus;
import org.udg.trackdev.spring.entity.enums.TaskType;
import org.udg.trackdev.spring.utils.Constants;

import javax.persistence.*;
import java.util.*;

/**
 * The type Task.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tasks")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    private String pullRequest;

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

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type.toString();
    }

    /**
     * Gets status text.
     *
     * @return the status text
     */
    public String getStatusText() {
        return status.toString();
    }

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
     * Add child task.
     *
     * @param task the task
     */
    public void addChildTask(Task task) {
        this.childTasks.add(task);
    }

    /**
     * Add points review.
     *
     * @param pointsReview the points review
     */
    public void addPointsReview(PointsReview pointsReview) {
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

    private void checkCanMoveToStatus(TaskStatus status) {
        /**if(this.status == TaskStatus.BACKLOG && status != TaskStatus.TODO) {
         throw new EntityException(String.format("Cannot change status from BACKLOG to new status <%s>", status));
         }
         if(status == TaskStatus.BACKLOG) {
         throw new EntityException("Cannot set status to BACKLOG");
         }**/
    }
}
