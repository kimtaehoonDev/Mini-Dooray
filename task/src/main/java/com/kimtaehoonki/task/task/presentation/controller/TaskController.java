package com.kimtaehoonki.task.task.presentation.controller;

import com.kimtaehoonki.task.task.presentation.dto.DeleteTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.GetMilestoneInTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.GetTagsInTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.GetTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.RegisterTaskRequestDto;
import com.kimtaehoonki.task.task.presentation.dto.RegisterTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.UpdateTaskRequestDto;
import com.kimtaehoonki.task.task.presentation.dto.UpdateTaskResponseDto;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
    /**
     * 태스크를 등록한다.
     *
     * @param requestDto @RequestBody
     * @return RegisterTaskResponseDto
     */
    @PostMapping("/tasks")
    public RegisterTaskResponseDto registerTask(@RequestBody RegisterTaskRequestDto requestDto) {
        return null;
    }

    /**
     * 태스크 목록을 확인한다(개수는 서버에서 정하기).
     *
     * @param page @RequestParam
     * @return GetTasksResponseDto
     */
    @GetMapping("/tasks")
    public List<GetTaskResponseDto> getTasks(@RequestParam(required = false) int page) {
        return null;
    }

    /**
     * 특정 태스크 상세정보를 확인한다.
     *
     * @param taskId @PathVariable
     * @return GetTaskResponseDto
     */
    @GetMapping("/tasks/{id}")
    public GetTaskResponseDto getTask(@PathVariable("id") Long taskId) {
        return null;
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
        return null;
    }

    /**
     * 특정 태스크를 삭제한다.
     *
     * @param taskId @PathVariable
     * @return DeleteTaskResponseDto
     */
    @DeleteMapping("/tasks/{id}")
    public DeleteTaskResponseDto deleteTask(@PathVariable("id") Long taskId) {
        return null;
    }

    /**
     * 해당 태스크의 태그들을 조회한다.
     *
     * @param taskId @PathVariable
     * @return GetTagsInTaskResponseDto
     */
    @GetMapping("/tasks/{id}/tags")
    public List<GetTagsInTaskResponseDto> getTagsInTask(@PathVariable("id") Long taskId) {
        return null;
    }

    /**
     * 해당 태스크의 마일스톤(1개)를 조회한다.
     *
     * @param taskId @PathVariable
     * @return GetMilestoneInTaskResponseDto
     */
    @GetMapping("/tasks/{id}/milestones")
    public GetMilestoneInTaskResponseDto getMilestoneInTask(@PathVariable("id") Long taskId) {
        return null;
    }
}
