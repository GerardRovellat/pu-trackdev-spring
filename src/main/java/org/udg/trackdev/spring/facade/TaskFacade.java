package org.udg.trackdev.spring.facade;

import org.springframework.validation.BindingResult;
import org.udg.trackdev.spring.dto.response.*;
import org.udg.trackdev.spring.dto.request.tasks.MergePatchTaskDTO;
import org.udg.trackdev.spring.dto.request.tasks.NewChildTaskDTO;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface TaskFacade {

    List<TaskResponseDTO> getAll(Principal principal);

    TaskWithPointsReviewDTO getTask(Long id, Principal principal);

    List<CommentDTO> getComments(Long id, Principal principal);

    TaskResponseDTO editTask(Long id, MergePatchTaskDTO task, Principal principal);

    List<TaskChangeDTO> getHistory(Long id, Principal principal);

    Long createTask(Long id, NewChildTaskDTO request, Principal principal, BindingResult validation);

    void deleteTask(Long id, Principal principal);

    Map<String,String> getListOfStatus();

    Map<String,String> getListOfUsStatus();

    Map<String,String> getListOfTaskStatus();

    Map<String,String> getListOfTypes();

    List<GithubPullRequestDTO> getPullRequest(Long projectId, Principal principal);

}
