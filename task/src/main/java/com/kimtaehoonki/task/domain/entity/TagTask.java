package com.kimtaehoonki.task.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TagTask 엔티티.
 */
@Entity
@Table(name = "Tags_Tasks")
public class TagTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tagsTasksId;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
