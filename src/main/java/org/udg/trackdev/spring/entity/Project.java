package org.udg.trackdev.spring.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.udg.trackdev.spring.entity.views.EntityLevelViews;
import org.udg.trackdev.spring.serializer.JsonHierarchyViewSerializer;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Project.
 */
@Entity
@Table(name = "PROJECTS")
@Data
@Builder
@AllArgsConstructor
public class Project extends BaseEntityLong {

   //-- CONSTANTS

    /**
     * The constant MIN_NAME_LENGTH.
     */
    public static final int MIN_NAME_LENGTH = 1;
    /**
     * The constant NAME_LENGTH.
     */
    public static final int NAME_LENGTH = 100;
    /**
     * The constant MIN_QUALIFICATION.
     */
    public static final int MIN_QUALIFICATION = 0;
    /**
     * The constant MAX_QUALIFICATION.
     */
    public static final int MAX_QUALIFICATION = 10;

    //-- ATTRIBUTES

    @NotNull
    @Column(length = NAME_LENGTH)
    private String name;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Course course;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Collection<Sprint> sprints = new ArrayList<>();

    @Max(10)
    private Double qualification;

    //--- CONSTRUCTOR

    /**
     * Instantiates a new Project.
     */
    public Project() {}

    /**
     * Instantiates a new Project.
     *
     * @param name the name
     */
    public Project(String name) {
        this.name = name;
    }

    //--- GETTERS AND SETTERS

    /**
     * Gets name.
     *
     * @return the name
     */
    @JsonView({EntityLevelViews.Basic.class})
    public String getName() { return this.name; }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Gets members.
     *
     * @return the members
     */
    @JsonView({EntityLevelViews.ProjectWithUser.class, EntityLevelViews.TaskWithProjectMembers.class})
    public Set<User> getMembers() { return this.members; }

    /**
     * Gets course.
     *
     * @return the course
     */
    @JsonView( { EntityLevelViews.Basic.class, EntityLevelViews.Hierarchy.class })
    @JsonSerialize(using = JsonHierarchyViewSerializer.class)
    public Course getCourse() {
        return this.course;
    }

    /**
     * Sets course.
     *
     * @param course the course
     */
    public void setCourse(Course course) {
        this.course = course;
    }

    /**
     * Gets tasks.
     *
     * @return the tasks
     */
    @JsonView({EntityLevelViews.ProjectComplete.class})
    public Collection<Task> getTasks() {
        Collection<Task> mainTasks = new ArrayList<>();
        this.tasks.stream().filter(task -> task.getParentTask() == null).forEach(mainTasks::add);
        return mainTasks;
    }

    /**
     * Gets sprints.
     *
     * @return the sprints
     */
    @JsonView({EntityLevelViews.ProjectComplete.class})
    public Collection<Sprint> getSprints() {
        return this.sprints;
    }

    /**
     * Gets qualification.
     *
     * @return the qualification
     */
    @JsonView({EntityLevelViews.Basic.class})
    public Double getQualification() { return this.qualification; }

    /**
     * Sets qualification.
     *
     * @param qualification the qualification
     */
    public void setQualification(Double qualification) { this.qualification = qualification; }

    //--- METHODS

    /**
     * Add member.
     *
     * @param member the member
     */
    public void addMember(User member) { this.members.add(member); }

    /**
     * Is member boolean.
     *
     * @param user the user
     * @return the boolean
     */
    public boolean isMember(User user) {
        return this.members.contains(user);
    }

    /**
     * Is member boolean.
     *
     * @param userId the user id
     * @return the boolean
     */
    public boolean isMember(String userId) {
        for(User user: this.members) {
            if(user.getId().equals(userId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove member.
     *
     * @param user the user
     */
    public void removeMember(User user) {
        this.members.remove(user);
    }

    /**
     * Add task.
     *
     * @param task the task
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Add sprint.
     *
     * @param sprint the sprint
     */
    public void addSprint(Sprint sprint) {
        this.sprints.add(sprint);
    }

}
