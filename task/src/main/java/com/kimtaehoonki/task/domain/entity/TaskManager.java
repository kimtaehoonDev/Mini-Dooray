package com.kimtaehoonki.task.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TaskManager 엔티티.
 */
@Entity
@Table(name = "task_managers")
public class TaskManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long taskManagersId;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private int managerId;

    private String managerName;
}
