package com.edutech.progressive.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.edutech.progressive.entity.Student;
import com.edutech.progressive.service.StudentService;

public class StudentServiceImplArraylist implements StudentService {

    private static List<Student> studentlist = new ArrayList<>();

    @Override
    public List<Student> getAllStudents() {
        return studentlist;
    }

    @Override
    public Integer addStudent(Student student) {
        studentlist.add(student);
        return studentlist.size();
    }

    @Override
    public List<Student> getAllStudentSortedByName() {
        Collections.sort(studentlist);
        return studentlist;
    }

    public void emptyArrayList() {
        studentlist = new ArrayList<>();
    }
}