package com.learn.sharding.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * School is the TENANT root.
 *
 * One school lives in exactly ONE shard.
 */
@Entity
@Table(name = "schools")
public class School {

    @Id
    private String schoolCode; // Tenant identifier

    private String name;

    protected School() {}

    public School(String schoolCode, String name) {
        this.schoolCode = schoolCode;
        this.name = name;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public String getName() {
        return name;
    }
}
