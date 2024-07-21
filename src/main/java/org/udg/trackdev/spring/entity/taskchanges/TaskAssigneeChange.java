package org.udg.trackdev.spring.entity.taskchanges;

import com.fasterxml.jackson.annotation.JsonView;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The type Task assignee change.
 */
@Entity
@DiscriminatorValue(value = TaskAssigneeChange.CHANGE_TYPE_NAME)
public class TaskAssigneeChange extends TaskChange {
    /**
     * The constant CHANGE_TYPE_NAME.
     */
    public static final String CHANGE_TYPE_NAME = "assignee_change";

    private String oldValue;
    private String newValue;

    /**
     * Instantiates a new Task assignee change.
     */
    public TaskAssigneeChange() {}

    /**
     * Instantiates a new Task assignee change.
     *
     * @param author    the author
     * @param taskId    the task id
     * @param oldValue  the old value
     * @param newValues the new values
     */
    public TaskAssigneeChange(String author, Long taskId, String oldValue, String newValues) {
        super(author, taskId);
        this.oldValue = oldValue;
        this.newValue = newValues;
    }

    @Override
    public String getType() {
        return CHANGE_TYPE_NAME;
    }

    /**
     * Gets old value.
     *
     * @return the old value
     */
    @JsonView(EntityLevelViews.Basic.class)
    public String getOldValue() { return this.oldValue; }

    /**
     * Gets new value.
     *
     * @return the new value
     */
    @JsonView(EntityLevelViews.Basic.class)
    public String getNewValue() { return this.newValue; }
}
