package com.kimtaehoonki.task.task.application;

import com.kimtaehoonki.task.task.application.dto.RegisterTaskServiceRequestDto;
import com.kimtaehoonki.task.task.presentation.dto.GetTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.UpdateTaskRequestDto;
import java.util.List;

public interface TaskService {

    Long registerTask(RegisterTaskServiceRequestDto requestDto);

    List<GetTaskResponseDto> showTasks(Long projectId, Integer page);

    GetTaskResponseDto showTask(Long taskId);

    Long updateTask(Long taskId, UpdateTaskRequestDto requestDto);

    void deleteTask(Long taskId);

    void showTagTasks(Long taskId);
}
