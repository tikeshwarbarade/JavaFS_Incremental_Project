package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.edutech.progressive.dao.StudentDAO;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.service.StudentService;

public class StudentServiceImplJdbc implements StudentService {

    private StudentDAO studentDAO;

    public StudentServiceImplJdbc(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public List<Student> getAllStudents() {
        try {
            return studentDAO.getAllStudents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer addStudent(Student student) {
        try {
            return studentDAO.addStudent(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> getAllStudentSortedByName() {
        try {
            List<Student> list = studentDAO.getAllStudents();
            Collections.sort(list);
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStudent(Student student) {
        try {
            studentDAO.updateStudent(student);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteStudent(int studentId) {
        try {
            studentDAO.deleteStudent(studentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}