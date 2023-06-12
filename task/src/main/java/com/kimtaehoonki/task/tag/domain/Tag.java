package com.kimtaehoonki.task.tag.domain;

import com.kimtaehoonki.task.colorcode.ColorCode;
import com.kimtaehoonki.task.project.domain.entity.Project;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
 * Tag 엔티티.
 */
@Entity
@Table(name = "tags")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "color_id")
    private ColorCode colorCode;

    public static Tag create(Project project, String name, ColorCode colorCode) {
        return new Tag(null, project, name, colorCode);
    }
}
