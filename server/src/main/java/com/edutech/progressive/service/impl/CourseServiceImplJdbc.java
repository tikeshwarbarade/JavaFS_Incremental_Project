package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.edutech.progressive.dao.CourseDAO;
import com.edutech.progressive.entity.Course;
import com.edutech.progressive.service.CourseService;

public class CourseServiceImplJdbc implements CourseService {

    private CourseDAO courseDAO;

    public CourseServiceImplJdbc(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public List<Course> getAllCourses() {
        try {
            return courseDAO.getAllCourses();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Course getCourseById(int courseId) {
        try {
            return courseDAO.getCourseById(courseId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer addCourse(Course course) {
        try {
            return courseDAO.addCourse(course);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateCourse(Course course) {
        try {
            courseDAO.updateCourse(course);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCourse(int courseId) {
        try {
            courseDAO.deleteCourse(courseId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}