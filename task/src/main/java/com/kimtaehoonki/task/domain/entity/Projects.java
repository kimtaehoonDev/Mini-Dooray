package com.kimtaehoonki.task.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long projectId;

    private int adminId;

    private String name;

    private String description;
}
