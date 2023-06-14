package com.kimtaehoonki.task.tag.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kimtaehoonki.task.TestConfig;
import com.kimtaehoonki.task.tag.ColorCode;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.tag.dto.TagResponseDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("dev")
class TagRepositoryTest {
    @Autowired
    TagRepository tagRepository;

    @Autowired
    TestEntityManager em;

    @Test
    void 프로젝트_아이디로_태그를_조회한다() {
        Project project1 = Project.make(1, null, null);
        em.persist(project1);

        Project project2 = Project.make(1, null, null);
        em.persist(project2);

        Tag tag1 = Tag.create(project1, "자바", ColorCode.BLUE);
        em.persist(tag1);
        Tag tag2 = Tag.create(project2, "파이썬", ColorCode.BLUE);
        em.persist(tag2);
        Tag tag3 = Tag.create(project1, "C++", ColorCode.BLUE);
        em.persist(tag3);

        List<TagResponseDto> result = tagRepository.findAllByProject_id(project1.getId());
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("자바");
        assertThat(result.get(1).getName()).isEqualTo("C++");
    }
}