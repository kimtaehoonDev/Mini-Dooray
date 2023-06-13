package com.kimtaehoonki.task.milestone.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kimtaehoonki.task.TestConfig;
import com.kimtaehoonki.task.milestone.dto.MilestoneResponseDto;
import com.kimtaehoonki.task.project.domain.entity.Project;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("dev")
class MilestoneRepositoryTest {
    @Autowired
    MilestoneRepository milestoneRepository;

    @Autowired
    TestEntityManager em;

    @Test
    void 프로젝트_아이디로_마일스톤을_조회한다() {
        Project project1 = Project.make(1, null, null);
        em.persist(project1);

        Project project2 = Project.make(1, null, null);
        em.persist(project2);

        Milestone milestone1 = Milestone.create(project1, "1주차", LocalDate.of(2023, 10, 10),
            LocalDate.of(2023, 10, 20));
        em.persist(milestone1);
        Milestone milestone2 = Milestone.create(project2, "1주차", LocalDate.of(2023, 1, 2),
            LocalDate.of(2023, 1, 12));
        em.persist(milestone2);
        Milestone milestone3 = Milestone.create(project1, "2주차", LocalDate.of(2023, 10, 21),
            LocalDate.of(2023, 10, 30));
        em.persist(milestone3);

        List<MilestoneResponseDto> result =
            milestoneRepository.findAllByProject_id(project1.getId());
        assertThat(result).hasSize(2);
    }
}