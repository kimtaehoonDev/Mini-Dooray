package com.kimtaehoonki.task.task.application;

import com.kimtaehoonki.task.exception.impl.MilestoneNotFoundException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.exception.impl.TagNotFoundException;
import com.kimtaehoonki.task.exception.impl.TaskNotFoundException;
import com.kimtaehoonki.task.milestone.domain.Milestone;
import com.kimtaehoonki.task.milestone.domain.MilestoneRepository;
import com.kimtaehoonki.task.project.domain.ProjectRepository;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.tag.domain.TagRepository;
import com.kimtaehoonki.task.tagtask.TagTask;
import com.kimtaehoonki.task.tagtask.TagTaskRepository;
import com.kimtaehoonki.task.task.domain.Task;
import com.kimtaehoonki.task.task.domain.TaskRepository;
import com.kimtaehoonki.task.task.presentation.dto.GetTagTasksResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.GetTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.RegisterTaskRequestDto;
import com.kimtaehoonki.task.task.presentation.dto.UpdateTaskRequestDto;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;
    private final TagRepository tagRepository;
    private final TagTaskRepository tagTaskRepository;

    @Transactional
    @Override
    public Long createTask(RegisterTaskRequestDto requestDto) {
        Project findProject = projectRepository.findById(requestDto.getProjectId())
                .orElseThrow(ProjectNotFoundException::new);

        Integer index = taskRepository.countByProjectId(findProject.getId());

        Milestone milestone = null;
        if (Objects.nonNull(requestDto.getMilestoneId())) {
            milestone = milestoneRepository.findById(requestDto.getMilestoneId())
                    .orElseThrow(MilestoneNotFoundException::new);
        }

        Task task = Task.make(findProject, milestone, index, requestDto.getTitle(),
                requestDto.getContent(), requestDto.getWriterId(), requestDto.getWriterName());
        Task saveTask = taskRepository.save(task);

        if (Objects.nonNull(requestDto.getTaskIds())) {
            requestDto.getTaskIds().stream()
                    .map(taskId -> tagRepository.findById(taskId)
                            .orElseThrow(TagNotFoundException::new))
                    .forEach(tag -> tagTaskRepository.save(TagTask.make(tag, task)));
        }

        return saveTask.getId();
    }

    @Override
    public List<GetTaskResponseDto> showTasks(Long projectId, Integer page) {

        List<Task> allByProjectId =
                taskRepository.findAllByProjectId(projectId, Pageable.ofSize(page));

        return allByProjectId.stream().map(
                task -> new GetTaskResponseDto(
                        task.getProject().getId(),
                        task.getIndexInProject(),
                        task.getTitle(),
                        task.getContents(),
                        task.getWriterId(),
                        task.getWriterName(),
                        task.getCreatedAt()
                )).collect(Collectors.toList());

    }

    @Override
    public GetTaskResponseDto showTask(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);

        return new GetTaskResponseDto(
                task.getProject().getId(),
                task.getIndexInProject(),
                task.getTitle(),
                task.getContents(),
                task.getWriterId(),
                task.getWriterName(),
                task.getCreatedAt()
        );
    }

    @Transactional
    @Override
    public Long updateTask(Long taskId, UpdateTaskRequestDto requestDto) {
        Task findTask
                = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        findTask.update(requestDto.getTitle(), requestDto.getContents());
        return findTask.getId();
    }

    @Transactional
    @Override
    public void deleteTask(Long taskId) {
        Task findTask
                = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        taskRepository.delete(findTask);
    }

    @Override
    public List<GetTagTasksResponseDto> showTagTasks(Long taskId) {
        List<TagTask> allByTaskId = tagTaskRepository.findAllByTaskId(taskId);
        allByTaskId.stream().map(tagTask -> {
            return tagTask.
        })
        return null;
    }
}
