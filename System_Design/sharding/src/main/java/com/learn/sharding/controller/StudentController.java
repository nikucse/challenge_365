package com.learn.sharding.controller;

import com.learn.sharding.entity.Student;
import com.learn.sharding.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Student APIs.
 *
 * Requires:
 * - X-School-Code header
 * - TenantFilter must run first
 */
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * CREATE Student
     *
     * WRITE -> PRIMARY
     */
    @PostMapping
    public ResponseEntity<String> createStudent(
            @RequestParam String name,
            @RequestParam int grade) {

        studentService.createStudent(name, grade);
        return ResponseEntity.ok("Student created");
    }

    /**
     * GET all students
     *
     * READ -> REPLICA
     */
    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
}
