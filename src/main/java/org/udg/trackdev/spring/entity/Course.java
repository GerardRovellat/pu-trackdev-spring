package org.udg.trackdev.spring.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

/**
 * The type Course.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "COURSES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntityLong {

    /**
     * Instantiates a new Course.
     *
     * @param startYear the start year
     */
    public Course(Integer startYear) {
        this.startYear = startYear;
    }

    private Integer startYear;

    private String githubOrganization;

    @ManyToOne
    private Subject subject;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
    private Collection<Project> projects;

    /**
     * Add project.
     *
     * @param project the project
     */
    public void addProject(Project project) { this.projects.add(project); }

}
