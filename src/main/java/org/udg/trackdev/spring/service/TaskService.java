package org.udg.trackdev.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.trackdev.spring.controller.exceptions.ServiceException;
import org.udg.trackdev.spring.dto.request.tasks.MergePatchTaskDTO;
import org.udg.trackdev.spring.entity.*;
import org.udg.trackdev.spring.entity.taskchanges.*;
import org.udg.trackdev.spring.repository.TaskRepository;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService extends BaseServiceLong<Task, TaskRepository> {

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    TaskChangeService taskChangeService;

    @Autowired
    SprintService sprintService;

    @Autowired
    CommentService commentService;

    @Autowired
    AccessChecker accessChecker;

    @Autowired
    PointsReviewService pointsReviewService;

    @Transactional
    public Task createTask(Long projectId, String name, String userId) {
        Project project = projectService.get(projectId);
        User user = userService.get(userId);
        accessChecker.checkCanViewProject(project, userId);
        Task task = new Task(name, user);
        task.setType(TaskType.USER_STORY);
        task.setProject(project);
        project.addTask(task);
        this.repo.save(task);

        return task;
    }

    @Transactional
    public Task createSubTask(Long taskId, String name, String userId) {
        Task parentTask = this.get(taskId);
        User user = userService.get(userId);
        accessChecker.checkCanViewProject(parentTask.getProject(), userId);
        Task subtask = new Task(name, user);
        subtask.setProject(parentTask.getProject());
        subtask.setType(TaskType.TASK);
        subtask.setParentTask(parentTask);
        subtask.setStatus(TaskStatus.DEFINED);
        parentTask.addChildTask(subtask);
        this.repo.save(subtask);

        return subtask;
    }

    @Transactional
    public Task editTask(Long id, MergePatchTaskDTO editTask, String userId) {
        Task task = get(id);
        User user = userService.get(userId);
        accessChecker.checkCanViewProject(task.getProject(), userId);

        List<TaskChange> changes = new ArrayList<>();

        editNameTreatment(task, user, editTask, changes);
        editDescriptionTreatment(task, editTask, user, changes);
        editReporterTreatment(task, editTask, user, changes);
        editAssigneeTreatment(task, editTask, user, changes);
        editEstimationPointsTreatment(task, editTask, user, changes);
        editStatusTreatment(task, editTask, user, changes);
        editRankTreatment(task, editTask, user, changes);
        editActiveSprintsTreatment(task, editTask, user, changes);
        editCommentTreatment(task, editTask, user, changes);
        editPointsReviewTreatment(task, editTask, user, changes);

        repo.save(task);
        for(TaskChange change: changes) {
            taskChangeService.store(change);
        }
        return task;
    }


    @Transactional
    public void deleteTask(Long id, String userId) {
        Task task = get(id);
        User user = userService.get(userId);
        accessChecker.checkCanViewProject(task.getProject(), userId);
        task.getActiveSprints().stream().forEach(sprint -> sprint.removeTask(task));
        task.setActiveSprints(new ArrayList<>());
        if (task.getParentTask() == null){
            Collection<Task> removeTask = task.getChildTasks();
            removeTask.stream().forEach(childTask -> childTask.setParentTask(null));
            removeTask.stream().forEach(childTask -> childTask.getActiveSprints().stream().forEach(sprint -> sprint.removeTask(childTask)));
            repo.deleteAll(removeTask);
        }
        repo.delete(task);
    }

    public Map<String,String> getListOfStatus() {
        Map<String,String> status = new HashMap<>();
        for (TaskStatus taskStatus : TaskStatus.values()) {
            status.put(taskStatus.name(), taskStatus.toString());
        }
        return status;
    }

    public Map<String,String> getListOfUsStatus() {
        int i_start = TaskStatus.BACKLOG.ordinal();
        int i_end = TaskStatus.DONE.ordinal();
        Map<String,String> status = new HashMap<>();
        for (int i = i_start; i <= i_end; i++ ) {
            TaskStatus taskStatus = TaskStatus.values()[i];
            status.put(taskStatus.name(), taskStatus.toString());
        }
        return status;
    }

    public Map<String,String> getListOfTaskStatus() {
        Map<String,String> status = new HashMap<>();
        status.put(TaskStatus.DEFINED.name(), TaskStatus.DEFINED.toString());
        status.put(TaskStatus.INPROGRESS.name(), TaskStatus.INPROGRESS.toString());
        status.put(TaskStatus.DONE.name(), TaskStatus.DONE.toString());
        return status;
    }

    public Map<String,String> getListOfTypes() {
        Map<String,String> types = new HashMap<>();
        for (TaskType taskType : TaskType.values()) {
            types.put(taskType.name(),taskType.toString());
        }
        return types;
    }

    public Collection<Comment> getComments(Long taskId) {
        return commentService.getComments(taskId);
    }

    private Collection<TaskChange> updateOtherTasksRank(User user, Integer newRank, Integer currentRank) {
        List<TaskChange> changes = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        int increase = 0;
        if(newRank > currentRank) {
            //tasks = repo().findAllBetweenRanksOfBacklog(backlogId, currentRank +1, newRank);
            increase = -1;
        } else if (newRank < currentRank){
            //tasks = repo().findAllBetweenRanksOfBacklog(backlogId, newRank, currentRank -1);
            increase = 1;
        }
        for(Task otherTask : tasks) {
            Integer otherNewRank = otherTask.getRank() + increase;
            otherTask.setRank(otherNewRank);
            //changes.add(new TaskRankChange(user, otherTask, otherNewRank));
        }

        return changes;
    }

    private void editNameTreatment(Task task, User user, MergePatchTaskDTO editTask, List<TaskChange> changes){
        if(editTask.getName()!= null) {
            if (editTask.getName().get() != null) {
                String oldName = task.getName();
                task.setName(editTask.getName().get());
                changes.add(new TaskNameChange(user.getEmail(), task.getId(), oldName, editTask.getName().get()));
            }
            else {
                throw new ServiceException(ErrorConstants.CAN_NOT_BE_NULL);
            }
        }
    }

    private void editDescriptionTreatment(Task task, MergePatchTaskDTO editTask, User user, List<TaskChange> changes){
        if(editTask.getDescription() != null) {
            if (editTask.getDescription().get() != null) {
                task.setDescription(editTask.getDescription().get());
            }
            task.setDescription(null);
        }
    }

    private void editReporterTreatment(Task task, MergePatchTaskDTO editTask, User user, List<TaskChange> changes){
        if(editTask.getReporter() != null) {
            User reporterUser;
            reporterUser = userService.getByEmail(editTask.getReporter().get());
            if (!task.getProject().isMember(reporterUser)) {
                throw new ServiceException(ErrorConstants.USER_NOT_PRJ_MEMBER);
            }
            task.setReporter(reporterUser);
        }
    }

    private void editAssigneeTreatment(Task task, MergePatchTaskDTO editTask, User user, List<TaskChange> changes){
        if(editTask.getAssignee() != null) {
            User assigneeUser;
            String oldValue = task.getAssignee() != null ? task.getAssignee().getUsername() : null;
            if (editTask.getAssignee().get() != null) {
                assigneeUser = userService.getByEmail(editTask.getAssignee().get());
                if (!task.getProject().isMember(assigneeUser)) {
                    throw new ServiceException(ErrorConstants.USER_NOT_PRJ_MEMBER);
                }
                task.setAssignee(assigneeUser);
            } else {
                task.setAssignee(null);
            }
            if (!Objects.equals(oldValue, editTask.getAssignee().get())) {
                changes.add(new TaskAssigneeChange(user.getEmail(), task.getId(), oldValue, task.getAssignee().getUsername()));
            }
        }
    }

    private void editEstimationPointsTreatment(Task task, MergePatchTaskDTO editTask, User user, List<TaskChange> changes){
        if(editTask.getEstimationPoints() != null) {
            Integer oldPoints = task.getEstimationPoints();
            Integer points = editTask.getEstimationPoints().get();
            task.setEstimationPoints(points);
            changes.add(new TaskEstimationPointsChange(user.getEmail(), task.getId(), oldPoints, points));
        }
    }

    private void editStatusTreatment(Task task, MergePatchTaskDTO editTask, User user, List<TaskChange> changes){
        if(editTask.getStatus() != null) {
            TaskStatus status = editTask.getStatus().get();
            TaskStatus oldStatus = task.getStatus();
            task.setStatus(status);
            changes.add(new TaskStatusChange(user.getEmail(), task.getId(), oldStatus.toString(),  status.toString()));
        }
    }

    private void editRankTreatment(Task task, MergePatchTaskDTO editTask, User user, List<TaskChange> changes){
        if(editTask.getRank() != null) {
            Integer newRank = editTask.getRank().get();
            Integer currentRank = task.getRank();
            if(!Objects.equals(newRank, currentRank)) {
                changes.addAll(updateOtherTasksRank(user, newRank, currentRank));
                changes.add(new TaskRankChange(user.getEmail(), task.getId(), currentRank, newRank));
                task.setRank(newRank);
            }
        }
    }

    private void editActiveSprintsTreatment(Task task, MergePatchTaskDTO editTask, User user, List<TaskChange> changes){
        if(editTask.getActiveSprints() != null) {
            Collection<Long> sprintsIds = editTask.getActiveSprints().orElseThrow(
                    () -> new ServiceException(ErrorConstants.CAN_NOT_BE_NULL));
            String oldValues = task.getActiveSprints().stream().map(Sprint::getName).collect(Collectors.joining(","));
            Collection<Sprint> sprints = sprintService.getSpritnsByIds(sprintsIds);
            String newValues = sprints.stream().map(Sprint::getName).collect(Collectors.joining(","));
            task.getActiveSprints().forEach(sprint -> sprint.removeTask(task));
            task.setActiveSprints(sprints);
            sprints.forEach(sprint -> sprint.addTask(task, user));
            changes.add(new TaskActiveSprintsChange(user.getEmail(), task.getId(), oldValues, newValues));
        }
    }

    private void editCommentTreatment(Task task, MergePatchTaskDTO editTask, User user, List<TaskChange> changes){
        if(editTask.getComment() != null) {
            Comment comment = editTask.getComment().orElseThrow(
                    () -> new ServiceException(ErrorConstants.CAN_NOT_BE_NULL));
            task.addComment(commentService.addComment(comment.getContent(), user, task));
        }
    }

    private void editPointsReviewTreatment(Task task, MergePatchTaskDTO editTask, User user, List<TaskChange> changes){
        if(editTask.getPointsReview() != null) {
            PointsReview pointsReview = editTask.getPointsReview().orElseThrow(
                    () -> new ServiceException(ErrorConstants.CAN_NOT_BE_NULL));
            pointsReviewService.addPointsReview(pointsReview.getPoints(), pointsReview.getComment(), user, task);
        }
    }

}
