package com.edutech.progressive.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.repository.CourseRepository;



import com.edutech.progressive.entity.Course;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImplJpa implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // Used by tests
    public CourseServiceImplJpa(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Used by Spring
    public CourseServiceImplJpa() {
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(int courseId) {
        return courseRepository.findByCourseId(courseId);
    }

    @Override
    public Integer addCourse(Course course) {
        Course savedCourse = courseRepository.save(course);
        return savedCourse.getCourseId();
    }

    @Override
    public void updateCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(int courseId) {
        courseRepository.deleteById(courseId);
    }

    @Override
    public List<Course> getAllCourseByTeacherId(int teacherId) {
        return courseRepository.findAllByTeacherTeacherId(teacherId);
    }
}