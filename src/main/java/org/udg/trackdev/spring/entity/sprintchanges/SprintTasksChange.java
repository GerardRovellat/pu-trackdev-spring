package org.udg.trackdev.spring.entity.sprintchanges;

import org.udg.trackdev.spring.entity.Task;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * The type Sprint tasks change.
 */
@Entity
public abstract class SprintTasksChange extends SprintChange {

    /**
     * Instantiates a new Sprint tasks change.
     */
    public SprintTasksChange() {}

    /**
     * Instantiates a new Sprint tasks change.
     *
     * @param author the author
     * @param sprint the sprint
     * @param task   the task
     */
    public SprintTasksChange(String author, Long sprint, Task task) {
        super(author, sprint);
        this.task = task;
    }

    @ManyToOne
    private Task task;

    /**
     * Gets task.
     *
     * @return the task
     */
    public Task getTask() {
        return this.task;
    }
}
