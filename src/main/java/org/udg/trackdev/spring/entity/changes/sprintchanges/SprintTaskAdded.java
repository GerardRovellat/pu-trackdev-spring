package org.udg.trackdev.spring.entity.changes.sprintchanges;

import org.udg.trackdev.spring.entity.Task;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The type Sprint task added.
 */
@Entity
@DiscriminatorValue(value = SprintTaskAdded.CHANGE_TYPE_NAME)
public class SprintTaskAdded extends SprintTasksChange {
    /**
     * The constant CHANGE_TYPE_NAME.
     */
    public static final String CHANGE_TYPE_NAME = "task_added";

    /**
     * Instantiates a new Sprint task added.
     */
    public SprintTaskAdded() {}

    /**
     * Instantiates a new Sprint task added.
     *
     * @param author the author
     * @param sprint the sprint
     * @param task   the task
     */
    public SprintTaskAdded(String author, Long sprint, Task task) {
        super(author, sprint, task);
    }

    @Override
    public String getType() {
        return CHANGE_TYPE_NAME;
    }
}
