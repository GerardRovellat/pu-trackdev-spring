package org.udg.trackdev.spring.entity;

import lombok.*;
import org.udg.trackdev.spring.utils.Constants;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Project.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "PROJECTS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Project extends BaseEntityLong {

    @NotNull
    @Column(length = Constants.MAX_NAME_LENGTH)
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

    @Min(Constants.MIN_QUALIFICATION)
    @Max(Constants.MAX_QUALIFICATION)
    private Double qualification;

    //--- CONSTRUCTOR

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
     * Gets tasks.
     *
     * @return the tasks
     */
    public Collection<Task> getTasks() {
        Collection<Task> mainTasks = new ArrayList<>();
        this.tasks.stream().filter(task -> task.getParentTask() == null).forEach(mainTasks::add);
        return mainTasks;
    }

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
        return !this.members.contains(user);
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
