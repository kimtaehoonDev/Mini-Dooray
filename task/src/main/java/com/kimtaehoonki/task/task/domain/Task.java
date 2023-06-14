package com.kimtaehoonki.task.task.domain;

import com.kimtaehoonki.task.milestone.domain.Milestone;
import com.kimtaehoonki.task.project.domain.entity.Project;
import com.kimtaehoonki.task.tagtask.TagTask;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Task 도메인객체.
 */
@Entity
@Table(name = "tasks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private Milestone mileStone;

    private Integer indexInProject;

    private String title;

    private String contents;

    private Integer writerId;

    private String writerName;

    private LocalDateTime createdAt;


    public static Task make(Project project, Milestone mileStone, int indexInProject,
                            String title, String contents, int writerId,
                            String writerName) {
        return new Task(null, project, mileStone, indexInProject, title, contents, writerId,
                writerName, LocalDateTime.now());
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
