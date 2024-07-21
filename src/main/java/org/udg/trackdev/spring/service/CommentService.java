package org.udg.trackdev.spring.service;

import org.springframework.stereotype.Service;
import org.udg.trackdev.spring.entity.Comment;
import org.udg.trackdev.spring.entity.Task;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.repository.CommentRepository;

import java.util.Collection;

/**
 * The type Comment service.
 */
@Service
public class CommentService extends BaseServiceLong<Comment, CommentRepository>{

    /**
     * Add comment comment.
     *
     * @param content the content
     * @param author  the author
     * @param task    the task
     * @return the comment
     */
    public Comment addComment(String content, User author, Task task) {
        Comment comment = new Comment(content, author, task);
        repo.save(comment);
        return comment;
    }

    /**
     * Gets comments.
     *
     * @param taskId the task id
     * @return the comments
     */
    public Collection<Comment> getComments(Long taskId) {
        return repo.findByTaskId(taskId);
    }

}
