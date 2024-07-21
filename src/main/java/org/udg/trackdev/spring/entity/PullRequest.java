package org.udg.trackdev.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The type Pull request.
 */
@Entity
@Table(name = "pull_requests")
public class PullRequest extends BaseEntityUUID {

    /**
     * Instantiates a new Pull request.
     */
    public PullRequest() {}

    /**
     * Instantiates a new Pull request.
     *
     * @param url    the url
     * @param nodeId the node id
     */
    public PullRequest(String url, String nodeId) {
        this.url = url;
        this.nodeId = nodeId;
    }

    @Column(length = 32)
    @NotNull
    private String nodeId;

    @ManyToOne
    private Task task;

    @NotNull
    private String url;

    @NotNull
    @ManyToOne
    private User author;

    /**
     * Gets node id.
     *
     * @return the node id
     */
    public String getNodeId() {
        return nodeId;
    }

    /**
     * Sets node id.
     *
     * @param nodeId the node id
     */
    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
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
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
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
     * @param author the author
     */
    public void setAuthor(User author) {
        this.author = author;
    }
}
