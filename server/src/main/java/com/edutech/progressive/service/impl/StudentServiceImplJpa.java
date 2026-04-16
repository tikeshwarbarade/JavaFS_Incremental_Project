package com.edutech.progressive.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.dto.StudentDTO;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.exception.StudentAlreadyExistsException;
import com.edutech.progressive.repository.AttendanceRepository;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.repository.StudentRepository;
import com.edutech.progressive.service.StudentService;

@Service
public class StudentServiceImplJpa implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    // Used by tests
    public StudentServiceImplJpa(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentServiceImplJpa(StudentRepository studentRepository,
                                 EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public StudentServiceImplJpa(StudentRepository studentRepository,
                                 EnrollmentRepository enrollmentRepository,
                                 AttendanceRepository attendanceRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.attendanceRepository = attendanceRepository;
    }

    // Used by Spring
    public StudentServiceImplJpa() {
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Integer addStudent(Student student) {
        Student existingStudent = studentRepository.findByEmail(student.getEmail());
        if (existingStudent != null) {
            throw new StudentAlreadyExistsException("Student with this email already exists");
        }

        Student savedStudent = studentRepository.save(student);
        return savedStudent.getStudentId();
    }

    @Override
    public List<Student> getAllStudentSortedByName() {
        List<Student> students = studentRepository.findAll();
        Collections.sort(students);
        return students;
    }

    @Override
    public void updateStudent(Student student) {
        Student existingStudent = studentRepository.findByEmail(student.getEmail());
        if (existingStudent != null && existingStudent.getStudentId() != student.getStudentId()) {
            throw new StudentAlreadyExistsException("Another student with this email already exists");
        }

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(int studentId) {
        if (attendanceRepository != null) {
            attendanceRepository.deleteByStudent_StudentId(studentId);
        }

        if (enrollmentRepository != null) {
            enrollmentRepository.deleteByStudent_StudentId(studentId);
        }

        studentRepository.deleteById(studentId);
    }

    @Override
    public Student getStudentById(int studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    public void modifyStudentDetails(StudentDTO studentDTO) {
        // not implemented yet
    }
}