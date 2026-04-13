package com.edutech.progressive.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.repository.CourseRepository;

@Service
public class CourseServiceImplJpa {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() throws Exception {
        return courseRepository.findAll();
    }

    public Course getCourseById(int courseId) throws Exception {
        return courseRepository.findById(courseId).orElse(null);
    }

    public Integer addCourse(Course course) throws Exception {
        Course savedCourse = courseRepository.save(course);
        return savedCourse.getCourseId();
    }

    public void updateCourse(Course course) throws Exception {
        courseRepository.save(course);
    }

    public void deleteCourse(int courseId) throws Exception {
        courseRepository.deleteById(courseId);
    }

    public List<Course> getAllCourseByTeacherId(int teacherId) {
        return courseRepository.findAll()
                .stream()
                .filter(c -> c.getTeacherId() == teacherId)
                .collect(Collectors.toList());
    }
}