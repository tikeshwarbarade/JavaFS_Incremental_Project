package com.edutech.progressive.dao;

import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.entity.Teacher;

public class TeacherDAOImpl implements TeacherDAO {

    @Override
    public int addTeacher(Teacher teacher) {
       return -1;
    }

    @Override
    public Teacher getTeacherById(int teacherId) {
        return null;
    }

    @Override
    public void updateTeacher(Teacher teacher) {
    

    }

    @Override
    public void deleteTeacher(int teacherId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteTeacher'");
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> list = new ArrayList<>();
          return list;
    }



}
