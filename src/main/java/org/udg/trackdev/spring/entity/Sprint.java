package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.lang.NonNull;
import org.udg.trackdev.spring.entity.enums.SprintStatus;
import org.udg.trackdev.spring.entity.enums.TaskStatus;
import org.udg.trackdev.spring.service.Global;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * The type Sprint.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sprints")
public class Sprint extends BaseEntityLong {

    //-- ATTRIBUTES

    @NonNull
    private String name;

    private Date startDate;

    private Date endDate;

    @Column(name = "`status`")
    private SprintStatus status;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Collection<Task> activeTasks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    /**
     * Instantiates a new Sprint.
     *
     * @param name the name
     */
    public Sprint(String name) {
        this.name = name;
        this.status = SprintStatus.DRAFT;
    }

    /**
     * Gets start date.
     *
     * @return the start date
     */
    @JsonFormat(pattern = Global.SIMPLE_LOCALDATE_FORMAT)
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    @JsonFormat(pattern = Global.SIMPLE_LOCALDATE_FORMAT)
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Gets status text.
     *
     * @return the status text
     */
    public String getStatusText() {
        return this.status.toString();
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(SprintStatus status) {
        if (status == SprintStatus.ACTIVE) {
            for (Task task : this.activeTasks) {
                if (task.getStatus() == TaskStatus.BACKLOG) {
                    task.setStatus(TaskStatus.TODO);
                }
            }
        }
        this.status = status;
    }

    //--- METHODS

    /**
     * Add task.
     *
     * @param task     the task
     * @param modifier the modifier
     */
    public void addTask(Task task, User modifier) {
        this.activeTasks.add(task);
        if (this.status == SprintStatus.ACTIVE && task.getStatus() == TaskStatus.BACKLOG) {
            task.setStatus(TaskStatus.TODO);
        }
    }

    /**
     * Remove task.
     *
     * @param task the task
     */
    public void removeTask(Task task) {
        this.activeTasks.remove(task);
    }

    private boolean areAllTasksClosed() {
        return this.activeTasks.stream().allMatch(
                t -> t.getStatus() == TaskStatus.DONE);
    }
}
