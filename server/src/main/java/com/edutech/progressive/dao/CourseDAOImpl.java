package com.edutech.progressive.dao;

import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Course;
import com.edutech.progressive.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Course;
import com.edutech.progressive.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.edutech.progressive.config.DatabaseConnectionManager;
import com.edutech.progressive.entity.Course;
import com.edutech.progressive.entity.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public int addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO course (course_name, description, teacher_id) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDescription());

            if (course.getTeacher() != null) {
                ps.setInt(3, course.getTeacher().getTeacherId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public Course getCourseById(int courseId) throws SQLException {
        String sql = "SELECT * FROM course WHERE course_id = ?";
        Course course = null;

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setDescription(rs.getString("description"));

                int teacherId = rs.getInt("teacher_id");
                if (!rs.wasNull()) {
                    Teacher teacher = new Teacher();
                    teacher.setTeacherId(teacherId);
                    course.setTeacher(teacher);
                }
            }
        }
        return course;
    }

    @Override
    public void updateCourse(Course course) throws SQLException {
        String sql = "UPDATE course SET course_name = ?, description = ?, teacher_id = ? WHERE course_id = ?";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, course.getCourseName());
            ps.setString(2, course.getDescription());

            if (course.getTeacher() != null) {
                ps.setInt(3, course.getTeacher().getTeacherId());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setInt(4, course.getCourseId());
            ps.executeUpdate();
        }
    }

    @Override
    public void deleteCourse(int courseId) throws SQLException {
        String sql = "DELETE FROM course WHERE course_id = ?";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, courseId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course";

        try (Connection con = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getInt("course_id"));
                course.setCourseName(rs.getString("course_name"));
                course.setDescription(rs.getString("description"));

                int teacherId = rs.getInt("teacher_id");
                if (!rs.wasNull()) {
                    Teacher teacher = new Teacher();
                    teacher.setTeacherId(teacherId);
                    course.setTeacher(teacher);
                }

                courses.add(course);
            }
        }
        return courses;
    }
}