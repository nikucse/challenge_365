package com.learn.sharding.controller;

import com.learn.sharding.service.SchoolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * School (Tenant) bootstrap controller.
 *
 * This is typically:
 * - Admin-only
 * - Run once per school
 *
 * For demo purposes, it is open.
 */
@RestController
@RequestMapping("/schools")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    /**
     * Create a new School (Tenant).
     *
     * NOTE:
     * - schoolCode decides the shard
     * - No X-School-Code header required here
     */
    @PostMapping
    public ResponseEntity<String> createSchool(
            @RequestParam String schoolCode,
            @RequestParam String name) {

        schoolService.createSchool(schoolCode, name);
        return ResponseEntity.ok("School created successfully");
    }
}
