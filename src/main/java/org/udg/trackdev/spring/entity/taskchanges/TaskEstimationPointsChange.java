package org.udg.trackdev.spring.entity.taskchanges;

import com.fasterxml.jackson.annotation.JsonView;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The type Task estimation points change.
 */
@Entity
@DiscriminatorValue(value = TaskEstimationPointsChange.CHANGE_TYPE_NAME)
public class TaskEstimationPointsChange extends TaskChange {

    /**
     * The constant CHANGE_TYPE_NAME.
     */
    public static final String CHANGE_TYPE_NAME = "estimation_points_change";

    /**
     * Instantiates a new Task estimation points change.
     */
    public TaskEstimationPointsChange() { }

    /**
     * Instantiates a new Task estimation points change.
     *
     * @param user     the user
     * @param taskId   the task id
     * @param oldValue the old value
     * @param newValue the new value
     */
    public TaskEstimationPointsChange(String user, Long taskId, Integer oldValue, Integer newValue) {
        super(user, taskId);
        this.oldValue = oldValue == null ? null : oldValue.toString();
        this.newValue = newValue == null ? null : newValue.toString();
    }

    private String oldValue;

    private String newValue;

    /**
     * Gets old value.
     *
     * @return the old value
     */
    @JsonView(EntityLevelViews.Basic.class)
    public String getOldValue() {
        return oldValue;
    }

    /**
     * Gets new value.
     *
     * @return the new value
     */
    @JsonView(EntityLevelViews.Basic.class)
    public String getNewValue() {
        return newValue;
    }

    @Override
    public String getType() {
        return CHANGE_TYPE_NAME;
    }
}
