package org.udg.trackdev.spring.entity.sprintchanges;

import org.udg.trackdev.spring.entity.changes.EntityLogChange;

import javax.persistence.*;

/**
 * The type Sprint change.
 */
@Entity
@Table(name = "sprint_changes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class SprintChange extends EntityLogChange {

    /**
     * Instantiates a new Sprint change.
     */
    public SprintChange() { }

    /**
     * Instantiates a new Sprint change.
     *
     * @param author the author
     * @param sprint the sprint
     */
    public SprintChange(String author, Long sprint) {
        super(author, sprint);
    }
}