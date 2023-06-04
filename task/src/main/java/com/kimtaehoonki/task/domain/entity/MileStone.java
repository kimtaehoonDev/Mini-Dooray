package com.kimtaehoonki.task.domain.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "milestones")
public class MileStone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long milestoneId;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String name;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
