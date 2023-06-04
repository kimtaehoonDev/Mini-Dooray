package com.kimtaehoonki.task.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ColorCode 엔티티.
 */
@Entity
@Table(name = "color_code")
public class ColorCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private byte id;

    private String colorCode;
}
