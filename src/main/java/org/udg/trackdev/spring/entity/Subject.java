package org.udg.trackdev.spring.entity;

import lombok.*;
import org.springframework.lang.NonNull;
import org.udg.trackdev.spring.utils.Constants;

import javax.persistence.*;
import java.util.Collection;

/**
 * The type Subject.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subjects")
public class Subject extends BaseEntityLong {

    @NonNull
    @Column(length = Constants.MAX_NAME_LENGTH)
    private String name;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private User owner;

    @Column(name = "ownerId", insertable = false, updatable = false, length = Constants.UUID_LENGTH)
    private String ownerId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject")
    private Collection<Course> courses;

    @NonNull
    private String acronym;

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
     * Add course.
     *
     * @param course the course
     */
    public void addCourse(Course course) { this.courses.add(course); }
}
