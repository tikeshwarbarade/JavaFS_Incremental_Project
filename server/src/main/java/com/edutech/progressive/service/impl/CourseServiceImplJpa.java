package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.exception.CourseAlreadyExistsException;
import com.edutech.progressive.exception.CourseNotFoundException;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImplJpa implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    // used by tests
    public CourseServiceImplJpa(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // used by Spring
    public CourseServiceImplJpa() {
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(int courseId) {
        Course course = courseRepository.findByCourseId(courseId);
        if (course == null) {
            throw new CourseNotFoundException("Course not found with id: " + courseId);
        }
        return course;
    }

    @Override
    public Integer addCourse(Course course) {
        Course existingCourse = courseRepository.findByCourseName(course.getCourseName());
        if (existingCourse != null) {
            throw new CourseAlreadyExistsException("Course with this name already exists");
        }

        Course savedCourse = courseRepository.save(course);
        return savedCourse.getCourseId();
    }

    @Override
    public void updateCourse(Course course) {
        Course existingCourse = courseRepository.findByCourseId(course.getCourseId());
        if (existingCourse == null) {
            throw new CourseNotFoundException("Course not found with id: " + course.getCourseId());
        }

        Course sameNameCourse = courseRepository.findByCourseName(course.getCourseName());
        if (sameNameCourse != null && sameNameCourse.getCourseId() != course.getCourseId()) {
            throw new CourseAlreadyExistsException("Another course with this name already exists");
        }

        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(int courseId) {
        Course existingCourse = courseRepository.findByCourseId(courseId);
        if (existingCourse == null) {
            throw new CourseNotFoundException("Course not found with id: " + courseId);
        }

        courseRepository.deleteById(courseId);
    }

    @Override
    public List<Course> getAllCourseByTeacherId(int teacherId) {
        return courseRepository.findAllByTeacherTeacherId(teacherId);
    }
}