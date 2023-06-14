package com.kimtaehoonki.task.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kimtaehoonki.task.TestConfig;
import com.kimtaehoonki.task.project.application.dto.response.ProjectDetail;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.project.domain.repository.ProjectRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("dev")
@Import(TestConfig.class)
class ProjectRepositoryTest {
    @Autowired
    ProjectRepository projectRepository;

    @Test
    void 존재하는_프로젝트를_이름으로_조회하면_true를_반환한다() {
        Project project = Project.make(1, "Hello", null);
        projectRepository.save(project);

        boolean result = projectRepository.existsByName("Hello");
        assertThat(result).isTrue();
    }

    @Test
    void 존재하지_않는_프로젝트를_이름으로_조회하면_false를_반환한다() {
        Project project = Project.make(1, "Hello", null);
        projectRepository.save(project);

        boolean result = projectRepository.existsByName("Jello2");
        assertThat(result).isFalse();
    }

    @Test
    void 아이디로_조회할수_있다() {
        Project project = Project.make(1, "hello", null);
        projectRepository.save(project);

        ProjectDetail projectDetail = projectRepository.findById(project.getId(), ProjectDetail.class)
            .get();
        assertThat(projectDetail.getName()).isEqualTo("hello");
    }

    @Test
    void 존재하지_않는_아이디로_조회하면_비어있는_Optional객체를_반환한다() {
        Optional<ProjectDetail> projectDetailOpt = projectRepository.findById(1L, ProjectDetail.class);
        assertThat(projectDetailOpt).isEmpty();
    }
}