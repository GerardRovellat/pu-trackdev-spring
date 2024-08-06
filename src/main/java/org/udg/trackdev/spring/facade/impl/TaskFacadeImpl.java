package org.udg.trackdev.spring.facade.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.udg.trackdev.spring.controller.exceptions.ControllerException;
import org.udg.trackdev.spring.dto.TaskChangeDTO;
import org.udg.trackdev.spring.dto.request.tasks.MergePatchTaskDTO;
import org.udg.trackdev.spring.dto.request.tasks.NewChildTaskDTO;
import org.udg.trackdev.spring.dto.response.tasks.CommentDTO;
import org.udg.trackdev.spring.dto.response.tasks.PointsReviewDTO;
import org.udg.trackdev.spring.dto.response.tasks.TaskResponseDTO;
import org.udg.trackdev.spring.dto.response.tasks.TaskWithPointsReviewDTO;
import org.udg.trackdev.spring.facade.TaskFacade;
import org.udg.trackdev.spring.mappers.EntityMapper;
import org.udg.trackdev.spring.service.*;
import org.udg.trackdev.spring.utils.Constants;
import org.udg.trackdev.spring.utils.ErrorConstants;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskFacadeImpl implements TaskFacade {

    private final AuthService authService;

    private final AccessChecker accessChecker;

    private final TaskService taskService;

    private final PointsReviewService pointsReviewService;

    private final TaskChangeService taskChangeService;

    private final EntityMapper mapper;


    @Override
    public List<TaskResponseDTO> getAll(Principal principal) {
        accessChecker.checkCanViewAllTasks(authService.getLoggedInUserId(principal));
        return taskService.getAll().stream().map(mapper::taskEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public TaskWithPointsReviewDTO getTask(Long id, Principal principal) {
        TaskResponseDTO taskDTO = mapper.taskEntityToDTO(taskService.get(id));
        accessChecker.checkCanViewProject(taskService.get(id).getProject(), authService.getLoggedInUserId(principal));
        List<PointsReviewDTO> pointsReviewDto = pointsReviewService.getPointsReview(authService.getLoggedInUserId(principal))
                .stream().map(mapper::pointsEntityToDTO).collect(Collectors.toList());
        return new TaskWithPointsReviewDTO(taskDTO, pointsReviewDto);
    }

    @Override
    public List<CommentDTO> getComments(Long id, Principal principal) {
        accessChecker.checkCanViewProject(taskService.get(id).getProject(), authService.getLoggedInUserId(principal));
        return taskService.getComments(id).stream().map(mapper::commentEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public TaskResponseDTO editTask(Long id, MergePatchTaskDTO request, Principal principal) {
        if (request.getName().isPresent()){
            if (request.getName().get().isEmpty() || request.getName().get().length() > Constants.MAX_TASK_NAME_LENGTH) {
                throw new ControllerException(ErrorConstants.INVALID_TASK_NAME_LENGTH);
            }
        }
        return mapper.taskEntityToDTO(taskService.editTask(id, request, authService.getLoggedInUserId(principal)));
    }

    @Override
    public List<TaskChangeDTO> getHistory(Long id, Principal principal) {
        return taskChangeService.getTaskHistory(id).stream().map(mapper::taskChangeInheritEntityToDTO).collect(Collectors.toList());
    }

    @Override
    public Long createTask(Long id, NewChildTaskDTO request, Principal principal, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new ControllerException(ErrorConstants.INVALID_TASK_NAME_LENGTH);
        }
        return taskService.createTask(id, request.getName(), authService.getLoggedInUserId(principal)).getId();
    }

    @Override
    public void deleteTask(Long id, Principal principal) {
        taskService.deleteTask(id, authService.getLoggedInUserId(principal));
    }

    @Override
    public Map<String, String> getListOfStatus() {
        return taskService.getListOfStatus();
    }

    @Override
    public Map<String, String> getListOfUsStatus() {
        return taskService.getListOfUsStatus();
    }

    @Override
    public Map<String, String> getListOfTaskStatus() {
        return taskService.getListOfTaskStatus();
    }

    @Override
    public Map<String, String> getListOfTypes() {
        return taskService.getListOfTypes();
    }


}
