package com.kimtaehoonki.task.comment.domain;

import com.kimtaehoonki.task.task.domain.Task;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Comment 엔티티.
 */
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private LocalDateTime createdAt;

    private int writerId;

    private String writerName;

    @Setter
    private String contents;

    public static Comment create(Task task, int writerId, String writerName, String contents) {
        return new Comment(null, task, LocalDateTime.now(), writerId, writerName, contents);
    }

    public boolean isWritten(Integer writerId) {
        return this.writerId == writerId;
    }
}
