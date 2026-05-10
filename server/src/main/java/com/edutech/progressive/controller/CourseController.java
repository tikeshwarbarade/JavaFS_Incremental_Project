package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.exception.CourseAlreadyExistsException;
import com.edutech.progressive.exception.CourseNotFoundException;
import com.edutech.progressive.service.impl.CourseServiceImplJpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseServiceImplJpa courseServiceImplJpa;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAllCourses() {
        try {
            List<Course> courses = courseServiceImplJpa.getAllCourses();
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> getCourseById(@PathVariable int courseId) {
        try {
            Course course = courseServiceImplJpa.getCourseById(courseId);
            return ResponseEntity.ok(course);
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    "application/json;charset=UTF-8"
            },
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Integer> addCourse(@RequestBody Course course) {
        try {
            Integer courseId = courseServiceImplJpa.addCourse(course);
            return new ResponseEntity<>(courseId, HttpStatus.CREATED);
        } catch (CourseAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(
            value = "/{courseId}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    "application/json;charset=UTF-8"
            }
    )
    public ResponseEntity<Void> updateCourse(@PathVariable int courseId, @RequestBody Course course) {
        try {
            course.setCourseId(courseId);
            courseServiceImplJpa.updateCourse(course);
            return ResponseEntity.ok().build();
        } catch (CourseAlreadyExistsException e) {
            return ResponseEntity.badRequest().build();
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable int courseId) {
        try {
            courseServiceImplJpa.deleteCourse(courseId);
            return ResponseEntity.noContent().build();
        } catch (CourseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/teacher/{teacherId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAllCourseByTeacherId(@PathVariable int teacherId) {
        try {
            List<Course> courses = courseServiceImplJpa.getAllCourseByTeacherId(teacherId);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}