package com.learn.sharding.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "teachers")
public class Teacher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String subject;

    protected Teacher() {}

    public Teacher(String schoolCode, String name, String subject) {
        this.schoolCode = schoolCode;
        this.name = name;
        this.subject = subject;
    }
}
