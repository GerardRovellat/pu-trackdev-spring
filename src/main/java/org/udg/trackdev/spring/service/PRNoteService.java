package org.udg.trackdev.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.trackdev.spring.entity.PRNote;
import org.udg.trackdev.spring.repository.PRNoteRepository;

/**
 * The type Pr note service.
 */
@Service
public class PRNoteService extends BaseServiceLong<PRNote, PRNoteRepository> {

    /**
     * The Pr service.
     */
    @Autowired
    PullRequestService prService;

    /**
     * The User service.
     */
    @Autowired
    UserService userService;

}
