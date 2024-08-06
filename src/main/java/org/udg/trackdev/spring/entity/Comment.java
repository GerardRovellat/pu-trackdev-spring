package org.udg.trackdev.spring.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * The type Comment.
 */
@Entity
@Table(name = "comments")
public class Comment extends BaseEntityLong {

    /**
     * The constant MAX_LENGTH.
     */
    public static final int MAX_LENGTH = 1000;

    @Column(length = MAX_LENGTH)
    private String content;

    @ManyToOne
    @JoinColumn(name = "authorId")
    private User author;

    @ManyToOne
    @JoinColumn(name = "taskId")
    private Task task;

    private Date date;

    /**
     * Instantiates a new Comment.
     */
    public Comment() {}

    /**
     * Instantiates a new Comment.
     *
     * @param content the content
     * @param author  the author
     * @param task    the task
     */
    public Comment(String content, User author, Task task) {
        this.content = content;
        this.author = author;
        this.task = task;
        this.date = new Date();
    }

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param authorId the author id
     */
    public void setAuthor(User authorId) {
        this.author = authorId;
    }

    /**
     * Gets task.
     *
     * @return the task
     */
    public Task getTask() {
        return task;
    }

    /**
     * Sets task.
     *
     * @param task the task
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }
}
