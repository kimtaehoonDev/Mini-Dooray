package com.kimtaehoonki.task.tag;

import com.kimtaehoonki.task.colorcode.ColorCode;
import com.kimtaehoonki.task.project.domain.entity.Project;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Tag 엔티티.
 */
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String name;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private ColorCode colorCode;
}
