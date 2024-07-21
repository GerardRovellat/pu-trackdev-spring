package org.udg.trackdev.spring.entity.sprintchanges;

import org.udg.trackdev.spring.entity.Task;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The type Sprint task removed.
 */
@Entity
@DiscriminatorValue(value = SprintTaskRemoved.CHANGE_TYPE_NAME)
public class SprintTaskRemoved extends SprintTasksChange {
    /**
     * The constant CHANGE_TYPE_NAME.
     */
    public static final String CHANGE_TYPE_NAME = "task_removed";

    /**
     * Instantiates a new Sprint task removed.
     */
    public SprintTaskRemoved() {}

    /**
     * Instantiates a new Sprint task removed.
     *
     * @param author the author
     * @param sprint the sprint
     * @param task   the task
     */
    public SprintTaskRemoved(String author, Long sprint, Task task) {
        super(author, sprint, task);
    }

    @Override
    public String getType() {
        return CHANGE_TYPE_NAME;
    }
}
