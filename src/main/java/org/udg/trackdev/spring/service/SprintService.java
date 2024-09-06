package org.udg.trackdev.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.trackdev.spring.dto.request.sprints.SprintRequestDTO;
import org.udg.trackdev.spring.entity.Project;
import org.udg.trackdev.spring.entity.Sprint;
import org.udg.trackdev.spring.entity.changes.sprintchanges.*;
import org.udg.trackdev.spring.entity.enums.SprintStatus;
import org.udg.trackdev.spring.entity.User;
import org.udg.trackdev.spring.repository.SprintRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * The type Sprint service.
 */
@Service
@Slf4j
public class SprintService extends BaseServiceLong<Sprint, SprintRepository> {

    private static final int SPRINT_STATUS_CHANGE_RATE = 1000 * 2; //1 second

    /**
     * The Access checker.
     */
    @Autowired
    AccessChecker accessChecker;

    /**
     * The User service.
     */
    @Autowired
    UserService userService;

    /**
     * The Sprint change service.
     */
    @Autowired
    SprintChangeService sprintChangeService;

    /**
     * Create sprint.
     *
     * @param project   the project
     * @param name      the name
     * @param startDate the start date
     * @param endDate   the end date
     * @param userId    the user id
     * @return the sprint
     */
    @Transactional
    public Sprint create(Project project, String name, Date startDate, Date endDate, String userId) {
        User user = userService.get(userId);
        Sprint sprint = new Sprint(name);
        sprint.setStartDate(startDate);
        sprint.setEndDate(endDate);
        sprint.setProject(project);
        this.repo.save(sprint);
        return sprint;
    }

    /**
     * Edit sprint sprint.
     *
     * @param sprintId   the sprint id
     * @param editSprint the edit sprint
     * @param userId     the user id
     * @return the sprint
     */
    @Transactional
    public Sprint editSprint(Long sprintId, SprintRequestDTO editSprint, String userId) {
        Sprint sprint = get(sprintId);
        User user = userService.get(userId);
        List<SprintChange> changes = new ArrayList<>();
        if(editSprint.name != null) {
            String name = editSprint.name;
            if(!name.equals(sprint.getName())) {
                sprint.setName(name);
                changes.add(new SprintNameChange(user.getEmail(),sprintId,name));
            }
        }
        if(editSprint.startDate != null) {
            Date startDate = editSprint.startDate;
            if(!startDate.equals(sprint.getStartDate())) {
                sprint.setStartDate(startDate);
                changes.add(new SprintStartDateChange(user.getEmail(),sprintId,startDate));
            }
        }
        if(editSprint.endDate != null) {
            Date endDate = editSprint.endDate;
            if(!endDate.equals(sprint.getEndDate())) {
                sprint.setEndDate(endDate);
                changes.add(new SprintEndDateChange(user.getEmail(),sprintId,endDate));
            }
        }
        if(editSprint.status != null) {
            SprintStatus status = editSprint.status;
            if(status != sprint.getStatus()) {
                sprint.setStatus(status);
                changes.add(new SprintStatusChange(user.getEmail(),sprintId,status));
            }
        }
        repo.save(sprint);
        for (SprintChange change : changes) {
            sprintChangeService.store(change);
        }
        return sprint;
    }

    /**
     * Delete sprint.
     *
     * @param sprintId the sprint id
     */
    @Transactional
    public void deleteSprint(Long sprintId) {
        Sprint sprint = get(sprintId);
        repo.delete(sprint);
    }


    /**
     * Gets spritns by ids.
     *
     * @param sprintIds the sprint ids
     * @return the spritns by ids
     */
    public Collection<Sprint> getSpritnsByIds(Collection<Long> sprintIds) {
        return repo.findAllById(sprintIds);
    }

    /**
     * Trigger sprint status change.
     */
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    public void triggerSprintStatusChange() {
        log.info("--- Start triggering sprint status change");
        Collection<Sprint> sprintsToClose = repo.sprintsToClose();
        if (sprintsToClose.size() > 0) {
            log.info("-- Sprints to CLOSE status: " + sprintsToClose.size());
            for (Sprint sprint : sprintsToClose) {
                sprint.setStatus(SprintStatus.CLOSED);
            }
            repo.saveAll(sprintsToClose);
            log.info("-- Sprints to CLOSED status changed");
        }
        Collection<Sprint> sprintsToDraft = repo.sprintsToDraft();
        if(sprintsToDraft.size() > 0) {
            log.info("-- Sprints to DRAFT status: " + sprintsToDraft.size());
            for(Sprint sprint : sprintsToDraft) {
                sprint.setStatus(SprintStatus.DRAFT);
            }
            repo.saveAll(sprintsToDraft);
            log.info("-- Sprints to DRAFT status changed");
        }
        Collection<Sprint> sprintsToActive = repo.sprintsToActive();
        if(sprintsToActive.size() > 0) {
            log.info("-- Sprints to ACTIVE status: " + sprintsToActive.size());
            for (Sprint sprint : sprintsToActive) {
                sprint.setStatus(SprintStatus.ACTIVE);
            }
            repo.saveAll(sprintsToActive);
            log.info("-- Sprints to ACTIVE status changed");
        }
        log.info("--- Done triggering sprint status change");
    }
}
