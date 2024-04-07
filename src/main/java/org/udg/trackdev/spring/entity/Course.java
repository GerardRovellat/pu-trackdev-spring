package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;

import javax.persistence.*;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "COURSES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseEntityLong {

    //TODO: Change for costants located in Constants.java
    public static final int MIN_START_YEAR = 1900;
    public static final int MAX_START_YEAR = 9999;

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

    @JsonView({ EntityLevelViews.Basic.class, EntityLevelViews.Hierarchy.class })
    public Integer getStartYear() { return startYear; }

    @JsonView({ EntityLevelViews.CourseComplete.class, EntityLevelViews.Hierarchy.class })
    public Subject getSubject() { return this.subject; }

    @JsonIgnore
    public Collection<Project> getProjects() { return this.projects; }

    public void addProject(Project project) { this.projects.add(project); }

    @JsonView({ EntityLevelViews.Basic.class, EntityLevelViews.Hierarchy.class })
    public String getGithubOrganization() { return this.githubOrganization; }

}
