package com.kimtaehoonki.task.task.application;

import com.kimtaehoonki.task.task.presentation.dto.GetTagTasksResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.GetTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.RegisterTaskRequestDto;
import com.kimtaehoonki.task.task.presentation.dto.UpdateTaskRequestDto;
import java.util.List;

public interface TaskService {

    Long createTask(RegisterTaskRequestDto requestDto);

    List<GetTaskResponseDto> showTasks(Long projectId, Integer page);

    GetTaskResponseDto showTask(Long taskId);

    Long updateTask(Long taskId, UpdateTaskRequestDto requestDto);

    void deleteTask(Long taskId);

    List<GetTagTasksResponseDto> showTagTasks(Long taskId);
}
