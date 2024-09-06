package org.udg.trackdev.spring.entity.changes.taskchanges;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The type Task status change.
 */
@Entity
@DiscriminatorValue(value = TaskStatusChange.CHANGE_TYPE_NAME)
public class TaskStatusChange extends TaskChange {
    /**
     * The constant CHANGE_TYPE_NAME.
     */
    public static final String CHANGE_TYPE_NAME = "status_change";

    /**
     * Instantiates a new Task status change.
     */
    public TaskStatusChange() {}

    /**
     * Instantiates a new Task status change.
     *
     * @param author   the author
     * @param taskId   the task id
     * @param oldValue the old value
     * @param newValue the new value
     */
    public TaskStatusChange(String author, Long taskId, String oldValue, String newValue) {
        super(author, taskId);
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    private String oldValue;

    private String newValue;

    @Override
    public String getType() {
        return CHANGE_TYPE_NAME;
    }

    /**
     * Gets old value.
     *
     * @return the old value
     */
    public String getOldValue() {
        return this.oldValue;
    }

    /**
     * Gets new value.
     *
     * @return the new value
     */
    public String getNewValue() {
        return this.newValue;
    }
}
