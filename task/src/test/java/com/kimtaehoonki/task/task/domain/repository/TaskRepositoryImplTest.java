package com.kimtaehoonki.task.task.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kimtaehoonki.task.TestConfig;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.tag.ColorCode;
import com.kimtaehoonki.task.tag.domain.Tag;
import com.kimtaehoonki.task.tagtask.TagTask;
import com.kimtaehoonki.task.task.application.dto.TaskPreview;
import com.kimtaehoonki.task.task.domain.Task;
import com.kimtaehoonki.task.task.presentation.dto.GetTaskResponseDto;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("dev")
class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TestEntityManager em;

    @Test
    void 태스크_프리뷰를_가져온다() {
        Project project = Project.make(1, null, null);
        em.persist(project);

        Task task = Task.make(project, null, 1, "a", null, 1, null);
        em.persist(task);

        Task task2 = Task.make(project, null, 2, "zz", null, 1, null);
        em.persist(task2);

        Tag tag1 = Tag.create(project, "t1", ColorCode.BLUE);
        em.persist(tag1);
        Tag tag2 = Tag.create(project, "t2",  ColorCode.GREEN);
        em.persist(tag2);

        TagTask tagTask1 = TagTask.make(tag1, task);
        em.persist(tagTask1);
        TagTask tagTask2 = TagTask.make(tag2, task); //task에 tag2 단다
        em.persist(tagTask2);
        TagTask tagTask3 = TagTask.make(tag1, task2); // task2에 tag1 단다
        em.persist(tagTask3);


        List<TaskPreview> tasksPreview =
            taskRepository.getTasksPreview(project.getId(), PageRequest.of(0, 5));
        assertThat(tasksPreview).hasSize(2);


        for (TaskPreview getTaskResponseDto : tasksPreview) {
            if (getTaskResponseDto.getTaskId().equals(tagTask1.getId())) {
                assertThat(getTaskResponseDto.getTags()).hasSize(2);
            }
            if (getTaskResponseDto.getTaskId().equals(tagTask2.getId())) {
                assertThat(getTaskResponseDto.getTags()).hasSize(1);
            }
        }
    }

}