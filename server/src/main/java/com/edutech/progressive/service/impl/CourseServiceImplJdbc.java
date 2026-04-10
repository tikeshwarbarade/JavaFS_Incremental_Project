package com.edutech.progressive.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.dao.CourseDAO;
import com.edutech.progressive.entity.Course;
import com.edutech.progressive.service.CourseService;

public class CourseServiceImplJdbc implements CourseService  {

    private CourseDAO courseDAO;
    @Override
    public List<Course> getAllCourses() {
        List<Course> list = new ArrayList<>();
          return list;
    }

    @Override
    public Course getCourseById(int courseId) {
        return null;
    }

    @Override
    public Integer addCourse(Course course) {
       return -1;
    }

    @Override
    public void updateCourse(Course course) {
       
    }

    @Override
    public void deleteCourse(int courseId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCourse'");
    }
}