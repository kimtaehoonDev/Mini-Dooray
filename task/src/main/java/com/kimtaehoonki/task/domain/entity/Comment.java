package com.kimtaehoonki.task.domain.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.lang.model.element.Name;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Comment 엔티티.
 */
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private LocalDateTime createdAt;

    private int writerId;

    private String writerName;

    private String contents;
}
