package com.learn.sharding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

/**
 * Base entity for ALL tables.
 *
 * Enforces tenant (school) ownership at the data level.
 *
 * IMPORTANT:
 * - Every entity MUST extend this
 * - schoolCode is NOT nullable
 */
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "school_code", nullable = false, updatable = false)
    protected String schoolCode;

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }
}
