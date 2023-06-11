package com.kimtaehoonki.task.task.domain;

import com.kimtaehoonki.task.milestone.domain.Milestone;
import com.kimtaehoonki.task.project.domain.entity.Project;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;

/**
 * Task 엔티티.
 */
@Entity
@Table(name = "tasks")
@Getter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private Milestone mileStone;

    private int indexInProject;

    private String title;

    private String contents;

    private int writerId;

    private String writerName;

    private String email;

    private LocalDateTime createdAt;
}
