package com.kimtaehoonki.task.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kimtaehoonki.task.project.ProjectStatus;
import com.kimtaehoonki.task.TestConfig;
import com.kimtaehoonki.task.project.application.dto.response.ProjectPreview;
import com.kimtaehoonki.task.project.domain.entity.MemberInProject;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.project.domain.repository.MemberInProjectRepository;
import com.kimtaehoonki.task.project.domain.repository.ProjectRepository;
import java.lang.reflect.Field;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("dev")
@AutoConfigureTestDatabase
class MemberInProjectRepositoryTest {

    @Autowired
    private MemberInProjectRepository memberInProjectRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void 특정프로젝트에_해당회원이_등록되면_true를_반환한다() {
        Project project = Project.make(1, null, null);
        projectRepository.save(project);

        MemberInProject memberInProject1 = MemberInProject.make(project, 1);
        memberInProjectRepository.save(memberInProject1);

        boolean result = memberInProjectRepository.existsByProject_idAndMemberId(
            project.getId(), 1);

        assertThat(result).isTrue();
    }

    @Test
    void 특정프로젝트에_해당회원이_등록되지_않았으면_false를_반환한다_회원_아이디가_다른경우() {
        Project project = Project.make(1, null, null);
        projectRepository.save(project);

        MemberInProject memberInProject1 = MemberInProject.make(project, 2);
        memberInProjectRepository.save(memberInProject1);

        boolean result = memberInProjectRepository.existsByProject_idAndMemberId(
            project.getId(), 1);

        assertThat(result).isFalse();
    }

    @Test
    void 특정프로젝트에_해당회원이_등록되지_않았으면_false를_반환한다_프로젝트_아이디가_다른경우() {
        Project project = Project.make(1, null, null);
        projectRepository.save(project);

        Project project2 = Project.make(2, null, null);
        projectRepository.save(project2);

        MemberInProject memberInProject1 = MemberInProject.make(project, 2);
        memberInProjectRepository.save(memberInProject1);

        boolean result = memberInProjectRepository.existsByProject_idAndMemberId(
            2L, 1);

        assertThat(result).isFalse();
    }

    @Test
    void 멤버_아이디로_프로젝트들의_프리뷰를_반환한다() {
        Project project1 = Project.make(1, "project1", "hello");
        projectRepository.save(project1);
        Project project2 = Project.make(2, "p2", "hi");
        projectRepository.save(project2);
        MemberInProject memberInProject1 = MemberInProject.make(project1, 1);
        memberInProjectRepository.save(memberInProject1);
        MemberInProject memberInProject2 = MemberInProject.make(project2, 1);
        memberInProjectRepository.save(memberInProject2);
        MemberInProject memberInProject3 = MemberInProject.make(project1, 2);
        memberInProjectRepository.save(memberInProject3);

        List<ProjectPreview> resultAboutMember1 =
            memberInProjectRepository.findProjectsPreviewsUsingMemberId(1);
        List<ProjectPreview> resultAboutMember2 =
            memberInProjectRepository.findProjectsPreviewsUsingMemberId(2);
        assertThat(resultAboutMember1).hasSize(2);
        assertThat(resultAboutMember2).hasSize(1);
    }

    @Test
    void 멤버_아이디로_프로젝트들의_프리뷰를_반환할때_종료된_프로젝트는_포함하지_않는다()
        throws NoSuchFieldException, IllegalAccessException {
        Project project1 = Project.make(1, "project1", "hello");
        Project project2 = Project.make(2, "p2", "hi");

        Class<Project> projectClazz = Project.class;
        Field statusField = projectClazz.getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(project1, ProjectStatus.EXIT);

        projectRepository.save(project1);
        projectRepository.save(project2);

        MemberInProject memberInProject1 = MemberInProject.make(project1, 1);
        memberInProjectRepository.save(memberInProject1);
        MemberInProject memberInProject2 = MemberInProject.make(project2, 1);
        memberInProjectRepository.save(memberInProject2);

        List<ProjectPreview> result =
            memberInProjectRepository.findProjectsPreviewsUsingMemberId(1);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("p2");
    }
}