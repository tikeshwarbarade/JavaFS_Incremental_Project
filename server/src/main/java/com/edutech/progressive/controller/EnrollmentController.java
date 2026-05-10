package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Enrollment;
import com.edutech.progressive.service.impl.EnrollmentServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {

    @Autowired
    private EnrollmentServiceImpl enrollmentServiceImpl;

    @GetMapping
    public ResponseEntity<List<Enrollment>> getAllEnrollments() {
        try {
            return ResponseEntity.ok(enrollmentServiceImpl.getAllEnrollments());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<Integer> createEnrollment(@RequestBody Enrollment enrollment) {
        try {
            int id = enrollmentServiceImpl.createEnrollment(enrollment);
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{enrollmentId}")
    public ResponseEntity<Void> updateEnrollment(@PathVariable int enrollmentId,
                                                 @RequestBody Enrollment enrollment) {
        try {
            enrollment.setEnrollmentId(enrollmentId);
            enrollmentServiceImpl.updateEnrollment(enrollment);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{enrollmentId}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable int enrollmentId) {
        try {
            enrollmentServiceImpl.deleteEnrollment(enrollmentId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<Enrollment> getEnrollmentById(@PathVariable int enrollmentId) {
        try {
            return ResponseEntity.ok(enrollmentServiceImpl.getEnrollmentById(enrollmentId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getAllEnrollmentsByStudent(@PathVariable int studentId) {
        try {
            return ResponseEntity.ok(enrollmentServiceImpl.getAllEnrollmentsByStudent(studentId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Enrollment>> getAllEnrollmentsByCourse(@PathVariable int courseId) {
        try {
            return ResponseEntity.ok(enrollmentServiceImpl.getAllEnrollmentsByCourse(courseId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}