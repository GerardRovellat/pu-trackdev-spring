package org.udg.trackdev.spring.entity.taskchanges;

import com.fasterxml.jackson.annotation.JsonView;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The type Task rank change.
 */
@Entity
@DiscriminatorValue(value = TaskRankChange.CHANGE_TYPE_NAME)
public class TaskRankChange extends TaskChange {

    /**
     * The constant CHANGE_TYPE_NAME.
     */
    public static final String CHANGE_TYPE_NAME = "rank_change";

    /**
     * Instantiates a new Task rank change.
     */
    public TaskRankChange() { }

    /**
     * Instantiates a new Task rank change.
     *
     * @param user     the user
     * @param taskId   the task id
     * @param oldValue the old value
     * @param newValue the new value
     */
    public TaskRankChange(String user, Long taskId, Integer oldValue, Integer newValue) {
        super(user, taskId);
        this.oldValue = oldValue.toString();
        this.newValue = newValue.toString();
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
        return this.oldValue;
    }

    /**
     * Gets new value.
     *
     * @return the new value
     */
    @JsonView(EntityLevelViews.Basic.class)
    public String getNewValue() {
        return this.newValue;
    }

    @Override
    public String getType() {
        return CHANGE_TYPE_NAME;
    }
}
