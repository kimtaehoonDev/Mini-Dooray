package com.kimtaehoonki.task.task.application;

import com.kimtaehoonki.task.exception.impl.AuthorizedException;
import com.kimtaehoonki.task.exception.impl.MilestoneNotFoundException;
import com.kimtaehoonki.task.exception.impl.ProjectNotFoundException;
import com.kimtaehoonki.task.exception.impl.TagNotFoundException;
import com.kimtaehoonki.task.exception.impl.TaskNotFoundException;
import com.kimtaehoonki.task.member.AccountRestTemplate;
import com.kimtaehoonki.task.member.MemberResponseDto;
import com.kimtaehoonki.task.milestone.domain.Milestone;
import com.kimtaehoonki.task.milestone.domain.MilestoneRepository;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.project.domain.repository.MemberInProjectRepository;
import com.kimtaehoonki.task.project.domain.repository.ProjectRepository;
import com.kimtaehoonki.task.tag.domain.TagRepository;
import com.kimtaehoonki.task.tagtask.TagTask;
import com.kimtaehoonki.task.tagtask.TagTaskRepository;
import com.kimtaehoonki.task.task.application.dto.RegisterTaskServiceRequestDto;
import com.kimtaehoonki.task.task.domain.Task;
import com.kimtaehoonki.task.task.domain.repository.TaskRepository;
import com.kimtaehoonki.task.task.presentation.dto.GetTaskResponseDto;
import com.kimtaehoonki.task.task.presentation.dto.UpdateTaskRequestDto;
import java.util.List;
import java.util.Objects;
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
    private final AccountRestTemplate accountRt;
    private final MemberInProjectRepository memberInProjectRepository;


    @Transactional
    @Override
    public Long registerTask(RegisterTaskServiceRequestDto dto) {
        Integer memberId = dto.getMemberId();
        MemberResponseDto memberInfo = accountRt.getMemberInfo(memberId);

        boolean isProjectMember =
            memberInProjectRepository.existsByProject_idAndMemberId(dto.getProjectId(), memberId);
        if (!isProjectMember) {
            throw new AuthorizedException();
        }

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(ProjectNotFoundException::new);

        Milestone milestone = null;
        if (Objects.nonNull(dto.getMilestoneId())) {
            milestone = milestoneRepository.findById(dto.getMilestoneId())
                    .orElseThrow(MilestoneNotFoundException::new);
        }

        Integer index = taskRepository.countByProjectId(project.getId());
        Task task = Task.make(project, milestone, index, dto.getTitle(),
                dto.getContent(), dto.getMemberId(), memberInfo.getName());
        Task saveTask = taskRepository.save(task);

        if (Objects.nonNull(dto.getTagIds())) {
            dto.getTagIds().stream()
                    .map(taskId -> tagRepository.findById(taskId)
                            .orElseThrow(TagNotFoundException::new))
                    .forEach(tag -> tagTaskRepository.save(TagTask.make(tag, task)));
        }

        return saveTask.getId();
    }

    @Override
    public List<GetTaskResponseDto> showTasks(Long projectId, Pageable pageable) {

//        List<Task> allByProjectId =
//                taskRepository.findAllByProjectId(projectId, Pageable.ofSize(page));
//        return allByProjectId.stream().map(GetTaskResponseDto::to)
//            .collect(Collectors.toList());
        return null;
    }

    @Override
    public GetTaskResponseDto showTask(Long taskId) {
//        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
//        return GetTaskResponseDto.to(task);
        return null;
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
}
