package org.udg.trackdev.spring.service;

import org.springframework.stereotype.Service;
import org.udg.trackdev.spring.entity.taskchanges.TaskChange;
import org.udg.trackdev.spring.repository.TaskChangeRepository;

import java.util.List;

/**
 * The type Task change service.
 */
@Service
public class TaskChangeService extends BaseServiceLong<TaskChange, TaskChangeRepository> {

    /**
     * Store.
     *
     * @param taskChange the task change
     */
    public void store(TaskChange taskChange) {
        repo().save(taskChange);
    }


    public List<TaskChange> getTaskHistory(Long taskId) {
        return repo().findByEntityId(taskId);
    }

}
