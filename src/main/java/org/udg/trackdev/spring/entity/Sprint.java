package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.lang.NonNull;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;
import org.udg.trackdev.spring.serializer.JsonDateSerializer;
import org.udg.trackdev.spring.service.Global;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * The type Sprint.
 */
@Entity
@Table(name = "sprints")
public class Sprint extends BaseEntityLong {

    //-- ATTRIBUTES

    /**
     * The constant MIN_NAME_LENGTH.
     */
    public static final int MIN_NAME_LENGTH = 1;
    /**
     * The constant NAME_LENGTH.
     */
    public static final int NAME_LENGTH = 50;

    @NonNull
    private String name;

    @JsonView(EntityLevelViews.Basic.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date startDate;

    @JsonView(EntityLevelViews.Basic.class)
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date endDate;

    @Column(name = "`status`")
    private SprintStatus status;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Collection<Task> activeTasks = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    //--- CONSTRUCTOR

    /**
     * Instantiates a new Sprint.
     */
    public Sprint() {}

    /**
     * Instantiates a new Sprint.
     *
     * @param name the name
     */
    public Sprint(String name) {
        this.name = name;
        this.status = SprintStatus.DRAFT;
    }

    //--- GETTERS AND SETTERS

    /**
     * Gets name.
     *
     * @return the name
     */
    @NonNull
    @JsonView({EntityLevelViews.Basic.class, EntityLevelViews.TaskComplete.class})
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
     * Gets start date.
     *
     * @return the start date
     */
    @JsonView({EntityLevelViews.Basic.class, EntityLevelViews.TaskComplete.class})
    @JsonFormat(pattern = Global.SIMPLE_LOCALDATE_FORMAT)
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets start date.
     *
     * @param startDate the start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date.
     *
     * @return the end date
     */
    @JsonView({EntityLevelViews.Basic.class, EntityLevelViews.TaskComplete.class})
    @JsonFormat(pattern = Global.SIMPLE_LOCALDATE_FORMAT)
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
     */
    public void setEndDate(Date endDate) {
        Date oldValue = this.endDate;
        this.endDate = endDate;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    @JsonView({EntityLevelViews.Basic.class, EntityLevelViews.TaskComplete.class})
    public SprintStatus getStatus() { return this.status; }

    /**
     * Gets status text.
     *
     * @return the status text
     */
    @JsonView({EntityLevelViews.Basic.class, EntityLevelViews.TaskComplete.class})
    public String getStatusText() { return this.status.toString(); }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(SprintStatus status) {
        if(status == SprintStatus.ACTIVE) {
            for(Task task : this.activeTasks) {
                if(task.getStatus() == TaskStatus.BACKLOG) {
                    task.setStatus(TaskStatus.TODO);
                }
            }
        }
        this.status = status;
    }

    /**
     * Gets active tasks.
     *
     * @return the active tasks
     */
    @JsonView(EntityLevelViews.SprintComplete.class)
    public Collection<Task> getActiveTasks() {
        return this.activeTasks;
    }

    /**
     * Sets active tasks.
     *
     * @param tasks the tasks
     */
    public void setActiveTasks(Collection<Task> tasks) {
        this.activeTasks = tasks;
    }

    /**
     * Gets project.
     *
     * @return the project
     */
    @JsonView(EntityLevelViews.SprintComplete.class)
    public Project getProject() {
        return this.project;
    }

    /**
     * Sets project.
     *
     * @param project the project
     */
    public void setProject(Project project) {
        this.project = project;
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
        if(this.status == SprintStatus.ACTIVE && task.getStatus() == TaskStatus.BACKLOG) {
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
        boolean allClosed = this.activeTasks.stream().allMatch(
                t -> t.getStatus() == TaskStatus.DONE);
        return allClosed;
    }
}
