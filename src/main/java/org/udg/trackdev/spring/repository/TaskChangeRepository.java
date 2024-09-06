package org.udg.trackdev.spring.repository;

import org.springframework.stereotype.Repository;
import org.udg.trackdev.spring.entity.changes.taskchanges.TaskChange;

import java.util.List;

@Repository
public interface TaskChangeRepository extends BaseRepositoryLong<TaskChange> {

    List<TaskChange> findByEntityId(Long taskId);

}
