package com.kimtaehoonki.task.tagtask;

import com.kimtaehoonki.task.task.Task;
import com.kimtaehoonki.task.tag.Tag;
import javax.persistence.Column;
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
    @Column(name = "tags_tasks_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
