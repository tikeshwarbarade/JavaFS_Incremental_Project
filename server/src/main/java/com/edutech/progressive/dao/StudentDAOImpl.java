package com.edutech.progressive.dao;

import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.entity.Student;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public int addStudent(Student student) {
       return -1;
    }

    @Override
    public Student getStudentById(int studentId) {
             return null;
    }

    @Override
    public void updateStudent(Student student) {
        
    }

    @Override
    public void deleteStudent(int studentId) {
      
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student>  list = new ArrayList<>();
         return list;
    }

}
