package com.kimtaehoonki.task.milestone;

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

/**
 * MileStone 엔티티.
 */
@Entity
@Table(name = "milestones")
public class MileStone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
