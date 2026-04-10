package com.edutech.progressive.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.edutech.progressive.dao.StudentDAO;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.service.StudentService;

public class StudentServiceImplJdbc implements StudentService  {
private StudentDAO studentDAO;

@Override
public List<Student> getAllStudents() {
                List<Student> list = new ArrayList<>();
        return list;
}

@Override
public Integer addStudent(Student student) {
      return -1;
}

@Override
public List<Student> getAllStudentSortedByName() {

            List<Student> list = new ArrayList<>();
        return list;
}
public void updateStudent(Student student){

}
public void deleteStudent(int studentId){

}
public Student getStudentById(int studentid){
        return null;
}
}