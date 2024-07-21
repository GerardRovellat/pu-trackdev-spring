package org.udg.trackdev.spring.service;

import org.springframework.stereotype.Service;
import org.udg.trackdev.spring.entity.sprintchanges.SprintChange;
import org.udg.trackdev.spring.repository.SprintChangeRepository;

/**
 * The type Sprint change service.
 */
@Service
public class SprintChangeService extends BaseServiceLong<SprintChange, SprintChangeRepository> {

    /**
     * Store.
     *
     * @param sprintChange the sprint change
     */
    public void store(SprintChange sprintChange) {
        repo().save(sprintChange);
    }

}
