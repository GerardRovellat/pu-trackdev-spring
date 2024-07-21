package org.udg.trackdev.spring.entity.changes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import org.udg.trackdev.spring.entity.BaseEntityLong;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;
import org.udg.trackdev.spring.service.Global;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * The type Entity log change.
 */
@MappedSuperclass
public abstract class EntityLogChange extends BaseEntityLong {

    /**
     * Instantiates a new Entity log change.
     */
    public EntityLogChange() { }

    /**
     * Instantiates a new Entity log change.
     *
     * @param author   the author
     * @param entityid the entityid
     */
    public EntityLogChange(String author, Long entityid) {
        this.author = author;
        this.entityid = entityid;
        this.changedAt = LocalDateTime.now();
    }

    private String author;

    private Long entityid;

    // Used for specifications
    @Column(name = "entityId", insertable = false, updatable = false)
    private Long entityId;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime changedAt;

    // Used for specifications
    @Column(name = "type", insertable = false, updatable = false)
    private String type;

    /**
     * Gets author.
     *
     * @return the author
     */
    @JsonView(EntityLevelViews.Basic.class)
    public String getAuthor() { return this.author; }

    /**
     * Gets entity.
     *
     * @return the entity
     */
    @JsonIgnore
    public Long getEntity() { return this.entityid; }

    /**
     * Gets changed at.
     *
     * @return the changed at
     */
    @JsonView(EntityLevelViews.Basic.class)
    @JsonFormat(pattern = Global.SIMPLE_DATE_FORMAT)
    public LocalDateTime getChangedAt() { return this.changedAt; }

    /**
     * Gets type.
     *
     * @return the type
     */
    @JsonView(EntityLevelViews.Basic.class)
    public abstract String getType();
}