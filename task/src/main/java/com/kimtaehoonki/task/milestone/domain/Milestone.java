package com.kimtaehoonki.task.milestone.domain;

import com.kimtaehoonki.task.project.domain.entity.Project;
import java.time.LocalDate;
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

/**
 * Milestone 엔티티.
 */
@Entity
@Table(name = "milestones")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    public static Milestone create(Project project, String name, LocalDate startDate,
                                   LocalDate endDate) {
        return new Milestone(null, project, name, startDate, endDate);
    }
}
