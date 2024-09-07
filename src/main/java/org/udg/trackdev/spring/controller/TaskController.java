package org.udg.trackdev.spring.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.udg.trackdev.spring.dto.request.tasks.MergePatchTaskDTO;
import org.udg.trackdev.spring.dto.request.tasks.NewChildTaskDTO;
import org.udg.trackdev.spring.dto.response.*;
import org.udg.trackdev.spring.facade.TaskFacade;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * The type Task controller.
 */
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "6. Tasks")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/tasks")
public class TaskController extends BaseController {

    private final TaskFacade facade;

    /**
     * Search list.
     *
     * @param principal the principal
     * @return the list
     */
    @Operation(summary = "Get information of tasks", description = "Get information of tasks")
    @GetMapping
    public List<TaskResponseDTO> getAll(Principal principal) {
        return facade.getAll(principal);
    }

    /**
     * Gets task.
     *
     * @param principal the principal
     * @param id        the id
     * @return the task
     */
    @Operation(summary = "Get information of a specific task", description = "Get information of a specific task")
    @GetMapping(path = "/{id}")
    public TaskWithPointsReviewDTO getTask(@PathVariable("id") Long id, Principal principal) {
        return facade.getTask(id,principal);
    }

    /**
     * Gets comments.
     *
     * @param principal the principal
     * @param id        the id
     * @return the comments
     */
    @Operation(summary = "Get comments of the task", description = "Get comments of the task")
    @GetMapping(path = "/{id}/comments")
    public List<CommentDTO> getComments(@PathVariable("id") Long id, Principal principal) {
        return facade.getComments(id, principal);
    }

    /**
     * Edit task task.
     *
     * @param principal   the principal
     * @param id          the id
     * @param request the task request
     * @return the task
     */
    @Operation(summary = "Edit task information", description = "Edit task information")
    @PatchMapping(path = "/{id}")
    public TaskResponseDTO editTask(@Valid @RequestBody MergePatchTaskDTO request, @PathVariable(name = "id") Long id,
                         Principal principal) {
        return facade.editTask(id, request, principal);
    }

    /**
     * Gets history.
     *
     * @param principal the principal
     * @param id        the id
     * @return the history
     */
    @Operation(summary = "Get history of logs of the task", description = "Get history of logs of the task")
    @GetMapping(path = "/{id}/history")
    public List<TaskChangeDTO> getHistory(@PathVariable(name = "id") Long id, Principal principal) {
        return facade.getHistory(id,principal);
    }

    /**
     * Create subtask id object long.
     *
     * @param principal the principal
     * @param id        the id
     * @param request   the request
     * @param result    the result
     * @return the id object long
     */
    @Operation(summary = "Create task of User Story", description = "Create task of User Story")
    @PostMapping(path = "/{id}/subtasks")
    public Long createSubtask(@PathVariable(name = "id") Long id, Principal principal, @Valid @RequestBody NewChildTaskDTO request,
                                      BindingResult result) {
        return facade.createSubtask(id, request, principal, result);
    }

    /**
     * Delete tasks.
     *
     * @param principal the principal
     * @param id        the id
     */
    @Operation(summary = "Delete especific task", description = "Delete especific task")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTasks(@PathVariable(name = "id") Long id, Principal principal) {
        facade.deleteTask(id, principal);
        return okNoContent();
    }

    /**
     * Gets list of status.
     *
     * @return the list of status
     */
    @Operation(summary = "Get list of tasks status", description = "Get list of tasks status")
    @GetMapping("/status")
    public Map<String,String > getListOfStatus() {
        return facade.getListOfStatus();
    }

    /**
     * Gets list of us status.
     *
     * @return the list of us status
     */
    @Operation(summary = "Get list of US status", description = "Get list of US status")
    @GetMapping("/usstatus")
    public Map<String,String > getListOfUsStatus() {
        return facade.getListOfUsStatus();
    }

    /**
     * Gets list of task status.
     *
     * @return the list of task status
     */
    @Operation(summary = "Get list of task status", description = "Get list of task status")
    @GetMapping("/taskstatus")
    public Map<String,String > getListOfTaskStatus() {
        return facade.getListOfTaskStatus();
    }

    /**
     * Gets list of types.
     *
     * @return the list of types
     */
    @Operation(summary = "Get types of tasks", description = "Get types of tasks")
    @GetMapping("/types")
    public Map<String,String> getListOfTypes() {
        return facade.getListOfTypes();
    }

    @GetMapping("/pullrequests")
    public List<GithubPullRequestDTO> getPullRequests(@RequestParam Long projectId, Principal principal) {
        return facade.getPullRequest(projectId, principal);
    }

}
