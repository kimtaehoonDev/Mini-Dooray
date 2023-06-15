package com.kimtaehoonki.task.tagtask;

import com.kimtaehoonki.task.task.domain.Task;
import com.kimtaehoonki.task.tag.domain.Tag;
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
 * TagTask 엔티티.
 */

@Getter
@Entity
@Table(name = "Tags_Tasks")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TagTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tags_tasks_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public static TagTask make(Tag tag, Task task) {
        return new TagTask(null, tag, task);
    }
}
