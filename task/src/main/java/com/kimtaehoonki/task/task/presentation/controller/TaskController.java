package com.kimtaehoonki.task.task.presentation.controller;

import com.kimtaehoonki.task.exception.impl.PageParamInvalidException;
import com.kimtaehoonki.task.task.application.TaskService;
import com.kimtaehoonki.task.task.application.dto.RegisterTaskServiceRequestDto;
import com.kimtaehoonki.task.task.application.dto.TaskPreview;
import com.kimtaehoonki.task.task.presentation.dto.GetMilestoneInTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.GetTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.RegisterTaskRequestDto;
import com.kimtaehoonki.task.task.presentation.dto.RegisterTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.UpdateTaskRequestDto;
import com.kimtaehoonki.task.task.presentation.dto.UpdateTaskResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TaskController {
    public static final int PAGE_SIZE = 10;

    private final TaskService taskService;
    /**
     * 태스크를 등록한다.
     *
     * @param requestDto @RequestBody
     * @return RegisterTaskResponseDto
     */
    @PostMapping("/tasks")
    public RegisterTaskResponseDto registerTask(@RequestBody RegisterTaskRequestDto requestDto,
                                                @CookieValue Integer memberId) {
        RegisterTaskServiceRequestDto serviceRequestDto =
            new RegisterTaskServiceRequestDto(requestDto, memberId);
        Long taskId = taskService.registerTask(serviceRequestDto);

        return new RegisterTaskResponseDto(taskId);
    }

    /**
     * 태스크 목록을 확인한다(개수는 서버에서 정하기).
     *
     * @param page @RequestParam
     * @return GetTasksResponseDto
     */
    @GetMapping("/tasks")
    public List<TaskPreview> getTasks(@RequestParam(required = false) Integer page,
                                      @RequestParam Long projectId) {
        if (page == null) {
            page = 0;
        }
        if (page < 0) {
            throw new PageParamInvalidException();
        }
        PageRequest pageable = PageRequest.of(page, PAGE_SIZE);
        List<TaskPreview> taskPreviews = taskService.showTasks(projectId, pageable);
        return taskPreviews;
    }

    /**
     * 특정 태스크 상세정보를 확인한다.
     *
     * @param taskId @PathVariable
     * @return GetTaskResponseDto
     */
    @GetMapping("/tasks/{id}")
    public GetTaskResponseDto getTask(@PathVariable("id") Long taskId) {
        return taskService.showTask(taskId);
    }

    /**
     * 태스크를 수정할 수 있다.
     *
     * @param taskId @PathVariable
     * @param requestDto @RequestBody
     * @return UpdateTaskResponseDto
     */
    @PutMapping("/tasks/{id}")
    public UpdateTaskResponseDto updateTask(@PathVariable("id") Long taskId,
                                            @RequestBody UpdateTaskRequestDto requestDto) {
        Long id = taskService.updateTask(taskId, requestDto);
        return new UpdateTaskResponseDto(id);
    }

    /**
     * 특정 태스크를 삭제한다.
     *
     * @param taskId @PathVariable
     * @return DeleteTaskResponseDto
     */
    @DeleteMapping("/tasks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable("id") Long taskId) {
        taskService.deleteTask(taskId);
    }

}
