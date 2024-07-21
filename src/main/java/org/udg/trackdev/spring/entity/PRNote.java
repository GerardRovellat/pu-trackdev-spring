package org.udg.trackdev.spring.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The type Pr note.
 */
@Entity
@Table(name = "pr_notes")
public class PRNote extends BaseEntityLong {

    /**
     * Instantiates a new Pr note.
     *
     * @param pullRequest the pull request
     * @param subject     the subject
     * @param author      the author
     * @param url         the url
     * @param level       the level
     * @param type        the type
     */
    public PRNote(PullRequest pullRequest, User subject, User author, String url, Integer level, String type) {
        this.pullRequest = pullRequest;
        this.subject = subject;
        this.author = author;
        this.url = url;
        this.level = level;
        this.type = type;
    }

    @ManyToOne
    private PullRequest pullRequest;

    @ManyToOne
    private User subject;

    @ManyToOne
    private User author;

    private String url;

    private Integer level;

    private String type;
}
