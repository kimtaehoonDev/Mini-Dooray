package com.kimtaehoonki.task.task.application;

import com.kimtaehoonki.task.task.application.dto.RegisterTaskServiceRequestDto;
import com.kimtaehoonki.task.task.application.dto.TaskPreview;
import com.kimtaehoonki.task.task.presentation.dto.GetTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.UpdateTaskRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Long registerTask(RegisterTaskServiceRequestDto requestDto);

    List<TaskPreview> showTasks(Long projectId, Pageable pageable);

    GetTaskResponseDto showTask(Long taskId);

    Long updateTask(Long taskId, UpdateTaskRequestDto requestDto);

    void deleteTask(Long taskId);
}
