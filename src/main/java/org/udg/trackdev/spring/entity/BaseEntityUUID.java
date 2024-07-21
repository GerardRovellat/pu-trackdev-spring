package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.annotations.GenericGenerator;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * The type Base entity uuid.
 */
@MappedSuperclass
public abstract class BaseEntityUUID {
    /**
     * The constant UUID_LENGTH.
     */
    public static final int UUID_LENGTH = 36;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = UUID_LENGTH)
    private String id;

    /**
     * Gets id.
     *
     * @return the id
     */
    @JsonView({EntityLevelViews.Basic.class, EntityLevelViews.Hierarchy.class})
    public String getId() {
        return id;
    }
}