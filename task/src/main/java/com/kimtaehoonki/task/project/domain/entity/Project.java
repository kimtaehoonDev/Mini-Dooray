package com.kimtaehoonki.task.project.domain.entity;

import com.kimtaehoonki.task.ProjectStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Project 도메인객체.
 */
@Entity
@Table(name = "projects")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    private int adminId;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    public static Project make(int adminId, String name, String description) {
        return new Project(null, adminId, name, description, ProjectStatus.ACTIVATION);
    }

    public boolean checkAdmin(int adminId) {
        return this.adminId == adminId;
    }

    public boolean isExit() {
        return this.status == ProjectStatus.EXIT;
    }

    public void changeStatus(ProjectStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("상태값에는 Null이 들어올 수 없습니다");
        }
        this.status = status;
    }
}
