package com.kimtaehoonki.task.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kimtaehoonki.task.TestConfig;
import com.kimtaehoonki.task.comment.application.dto.response.CommentResponseDto;
import com.kimtaehoonki.task.task.domain.Task;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("dev")
@AutoConfigureTestDatabase
class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TestEntityManager em;

    @Test
    void 태스크_아이디로_게시물을_전체_조회한다() {
        Task task1 = new Task();
        em.persist(task1);

        Task task2 = new Task();
        em.persist(task2);

        Comment comment1 = Comment.create(task1, 1, null, null);
        em.persist(comment1);

        Comment comment2 = Comment.create(task1, 2, null, null);
        em.persist(comment2);

        Comment comment3 = Comment.create(task2, 2, null, null);
        em.persist(comment3);

        List<CommentResponseDto> result = commentRepository.findAllByTask_id(task1.getId());
        assertThat(result).hasSize(2);
    }

}