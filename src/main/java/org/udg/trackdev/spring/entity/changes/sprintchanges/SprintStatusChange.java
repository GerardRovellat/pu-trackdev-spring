package org.udg.trackdev.spring.entity.changes.sprintchanges;

import org.udg.trackdev.spring.entity.enums.SprintStatus;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The type Sprint status change.
 */
@Entity
@DiscriminatorValue(value = SprintStatusChange.CHANGE_TYPE_NAME)
public class SprintStatusChange extends SprintChange {
    /**
     * The constant CHANGE_TYPE_NAME.
     */
    public static final String CHANGE_TYPE_NAME = "status_change";

    /**
     * Instantiates a new Sprint status change.
     */
    public SprintStatusChange() { }

    /**
     * Instantiates a new Sprint status change.
     *
     * @param author the author
     * @param sprint the sprint
     * @param status the status
     */
    public SprintStatusChange(String author, Long sprint, SprintStatus status) {
        super(author, sprint);
        this.status = status;
    }

    @Column(name = "`status`")
    private SprintStatus status;

    @Override
    public String getType() {
        return CHANGE_TYPE_NAME;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public SprintStatus getStatus() {
        return this.status;
    }
}
