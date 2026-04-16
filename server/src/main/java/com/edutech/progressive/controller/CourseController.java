package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.exception.CourseAlreadyExistsException;
import com.edutech.progressive.exception.CourseNotFoundException;
import com.edutech.progressive.service.impl.CourseServiceImplJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseServiceImplJpa courseServiceImplJpa;

    // GET /course
    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            return ResponseEntity.ok(courseServiceImplJpa.getAllCourses());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /course/{courseId}
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable int courseId) {
        try {
            return ResponseEntity.ok(courseServiceImplJpa.getCourseById(courseId));
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // POST /course
    @PostMapping
    public ResponseEntity<Integer> addCourse(@RequestBody Course course) {
        try {
            return new ResponseEntity<>(courseServiceImplJpa.addCourse(course), HttpStatus.CREATED);
        } catch (CourseAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // PUT /course/{courseId}
    @PutMapping("/{courseId}")
    public ResponseEntity<Void> updateCourse(@PathVariable int courseId, @RequestBody Course course) {
        try {
            course.setCourseId(courseId);
            courseServiceImplJpa.updateCourse(course);
            return ResponseEntity.ok().build();
        } catch (CourseAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // DELETE /course/{courseId}
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int courseId) {
        try {
            courseServiceImplJpa.deleteCourse(courseId);
            return ResponseEntity.noContent().build();
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /course/teacher/{teacherId}
    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getAllCourseByTeacherId(@PathVariable int teacherId) {
        try {
            return ResponseEntity.ok(courseServiceImplJpa.getAllCourseByTeacherId(teacherId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
