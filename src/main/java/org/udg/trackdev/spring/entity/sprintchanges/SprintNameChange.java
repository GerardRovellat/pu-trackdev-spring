package org.udg.trackdev.spring.entity.sprintchanges;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * The type Sprint name change.
 */
@Entity
@DiscriminatorValue(value = SprintNameChange.CHANGE_TYPE_NAME)
public class SprintNameChange extends SprintChange {
    /**
     * The constant CHANGE_TYPE_NAME.
     */
    public static final String CHANGE_TYPE_NAME = "name_change";

    /**
     * Instantiates a new Sprint name change.
     */
    public SprintNameChange() {}

    /**
     * Instantiates a new Sprint name change.
     *
     * @param author the author
     * @param sprint the sprint
     * @param name   the name
     */
    public SprintNameChange(String author, Long sprint, String name) {
        super(author, sprint);
        this.name = name;
    }

    private String name;

    @Override
    public String getType() {
        return CHANGE_TYPE_NAME;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }
}
