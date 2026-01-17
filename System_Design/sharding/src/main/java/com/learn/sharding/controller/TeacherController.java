package com.learn.sharding.controller;
import com.learn.sharding.entity.Teacher;
import com.learn.sharding.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Teacher APIs.
 *
 * Same tenant rules as Student.
 */
@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    /**
     * WRITE -> PRIMARY
     */
    @PostMapping
    public ResponseEntity<String> createTeacher(
            @RequestParam String name,
            @RequestParam String subject) {

        teacherService.createTeacher(name, subject);
        return ResponseEntity.ok("Teacher created");
    }

    /**
     * READ -> REPLICA
     */
    @GetMapping
    public ResponseEntity<List<Teacher>> getTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }
}
