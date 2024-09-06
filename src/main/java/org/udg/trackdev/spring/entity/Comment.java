package org.udg.trackdev.spring.entity;

import lombok.*;
import org.udg.trackdev.spring.utils.Constants;

import javax.persistence.*;
import java.util.Date;

/**
 * The type Comment.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comments")
public class Comment extends BaseEntityLong {

    @Column(length = Constants.CONTENT_MAX_LENGTH)
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

}
