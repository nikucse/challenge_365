package com.learn.sharding.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int grade;

    protected Student() {}

    public Student(String schoolCode, String name, int grade) {
        this.schoolCode = schoolCode;
        this.name = name;
        this.grade = grade;
    }
}
