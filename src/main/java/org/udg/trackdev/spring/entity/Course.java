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
     * The constant MIN_START_YEAR.
     */
//TODO: Change for costants located in Constants.java
    public static final int MIN_START_YEAR = 1900;
    /**
     * The constant MAX_START_YEAR.
     */
    public static final int MAX_START_YEAR = 9999;

    /**
     * Instantiates a new Course.
     *
     * @param startYear the start year
     */
//TODO: Refactor to use empty constructor with setters
    public Course(Integer startYear) {
        this.startYear = startYear;
    }

    private Integer startYear;

    private String githubOrganization;

    //TODO: Refactor the relationship of the Coruse

    @ManyToOne
    private Subject subject;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course", fetch = FetchType.LAZY)
    private Collection<Project> projects;

    //TODO: Put JsonView in the attribute and refactor JsonViews

    /**
     * Gets start year.
     *
     * @return the start year
     */
    public Integer getStartYear() { return startYear; }

    /**
     * Gets subject.
     *
     * @return the subject
     */
    public Subject getSubject() { return this.subject; }

    /**
     * Gets projects.
     *
     * @return the projects
     */
    public Collection<Project> getProjects() { return this.projects; }

    /**
     * Add project.
     *
     * @param project the project
     */
    public void addProject(Project project) { this.projects.add(project); }

    /**
     * Gets github organization.
     *
     * @return the github organization
     */
    public String getGithubOrganization() { return this.githubOrganization; }

}
