package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.edutech.progressive.dao.TeacherDAO;
import com.edutech.progressive.entity.Teacher;
import com.edutech.progressive.service.TeacherService;

public class TeacherServiceImplJdbc implements TeacherService {

    private TeacherDAO teacherDAO;

    public TeacherServiceImplJdbc(TeacherDAO teacherDAO) {
        this.teacherDAO = teacherDAO;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        try {
            return teacherDAO.getAllTeachers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer addTeacher(Teacher teacher) {
        try {
            int id = teacherDAO.addTeacher(teacher);
            teacher.setTeacherId(id);
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Teacher> getTeacherSortedByExperience() {
        try {
            List<Teacher> list = teacherDAO.getAllTeachers();
            Collections.sort(list);
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        try {
            teacherDAO.updateTeacher(teacher);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTeacher(int teacherId) {
        try {
            teacherDAO.deleteTeacher(teacherId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}