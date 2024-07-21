package org.udg.trackdev.spring.entity.taskchanges;

import org.udg.trackdev.spring.entity.changes.EntityLogChange;

import javax.persistence.*;

/**
 * The type Task change.
 */
@Entity
@Table(name = "task_changes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class TaskChange extends EntityLogChange {

    /**
     * Instantiates a new Task change.
     */
    public TaskChange() { }

    /**
     * Instantiates a new Task change.
     *
     * @param author   the author
     * @param entityId the entity id
     */
    public TaskChange(String author, Long entityId) {
        super(author, entityId);
    }
}
