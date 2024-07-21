package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.lang.NonNull;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;

import javax.persistence.*;
import java.util.Collection;

/**
 * The type Subject.
 */
@Entity
@Table(name = "subjects")
public class Subject extends BaseEntityLong {

    /**
     * The constant MIN_NAME_LENGTH.
     */
    public static final int MIN_NAME_LENGTH = 1;
    /**
     * The constant NAME_LENGTH.
     */
    public static final int NAME_LENGTH = 50;
    /**
     * The constant MIN_ACRONYM_LENGTH.
     */
    public static final int MIN_ACRONYM_LENGTH = 2;

    /**
     * The constant MAX_ACRONYM_LENGTH.
     */
    public static final int MAX_ACRONYM_LENGTH = 5;

    @NonNull
    @Column(length = NAME_LENGTH)
    private String name;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private User owner;

    @Column(name = "ownerId", insertable = false, updatable = false, length = BaseEntityUUID.UUID_LENGTH)
    private String ownerId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    private Collection<Course> courses;

    @NonNull
    private String acronym;

    /**
     * Instantiates a new Subject.
     */
    public Subject() {}

    /**
     * Instantiates a new Subject.
     *
     * @param name    the name
     * @param acronym the acronym
     * @param owner   the owner
     */
    public Subject(String name, String acronym, User owner) {
        this.name = name;
        this.acronym = acronym;
        this.owner = owner;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    @JsonView({ EntityLevelViews.Basic.class, EntityLevelViews.Hierarchy.class })
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets owner id.
     *
     * @return the owner id
     */
    @JsonView(EntityLevelViews.Basic.class)
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Gets acronym.
     *
     * @return the acronym
     */
    @JsonView(EntityLevelViews.Basic.class)
    public String getAcronym() {
        return acronym;
    }

    /**
     * Sets owner.
     *
     * @param owner the owner
     */
    public void setOwner(@NonNull User owner) {
        this.owner = owner;
    }

    /**
     * Sets acronym.
     *
     * @param acronym the acronym
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    /**
     * Gets courses.
     *
     * @return the courses
     */
    @JsonView(EntityLevelViews.SubjectComplete.class)
    public Collection<Course> getCourses() {
        return this.courses;
    }


    /**
     * Add course.
     *
     * @param course the course
     */
    public void addCourse(Course course) { this.courses.add(course); }
}
