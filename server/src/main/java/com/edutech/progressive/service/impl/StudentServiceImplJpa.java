package com.edutech.progressive.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.edutech.progressive.dto.StudentDTO;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.repository.StudentRepository;
import com.edutech.progressive.service.StudentService;

@Service
public class StudentServiceImplJpa implements StudentService {

    private StudentRepository studentRepository;

    // ✅ Used by DaySixTest
    public StudentServiceImplJpa(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ✅ Used by Spring
    public StudentServiceImplJpa() {
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Integer addStudent(Student student) {
        Student savedStudent = studentRepository.save(student);
        return savedStudent.getStudentId();
    }

    @Override
    public List<Student> getAllStudentSortedByName() {
        List<Student> list = studentRepository.findAll();
        Collections.sort(list);
        return list;
    }

    @Override
    public void updateStudent(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(int studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public Student getStudentById(int studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    public void modifyStudentDetails(StudentDTO studentDTO) {
        // Not required until Day‑13
    }
}
