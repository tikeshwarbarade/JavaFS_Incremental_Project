package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Course;
import org.springframework.http.ResponseEntity;

import java.util.List;


import com.edutech.progressive.entity.Course;
import com.edutech.progressive.service.impl.CourseServiceImplJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseServiceImplJpa courseServiceImplJpa;

    // GET /course
    @GetMapping
    public List<Course> getAllCourses() {
        return courseServiceImplJpa.getAllCourses();
    }

    // GET /course/{courseId}
    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable int courseId) {
        return courseServiceImplJpa.getCourseById(courseId);
    }

    // POST /course
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int addCourse(@RequestBody Course course) {
        return courseServiceImplJpa.addCourse(course);
    }

    // PUT /course/{courseId}
    @PutMapping("/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCourse(@PathVariable int courseId, @RequestBody Course course) {
        course.setCourseId(courseId);
        courseServiceImplJpa.updateCourse(course);
    }

    // DELETE /course/{courseId}
    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable int courseId) {
        courseServiceImplJpa.deleteCourse(courseId);
    }

    // GET /course/teacher/{teacherId}
    @GetMapping("/teacher/{teacherId}")
    public List<Course> getAllCourseByTeacherId(@PathVariable int teacherId) {
        return courseServiceImplJpa.getAllCourseByTeacherId(teacherId);
    }
}
