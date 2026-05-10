package com.edutech.progressive.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.progressive.dto.StudentDTO;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.exception.StudentAlreadyExistsException;
import com.edutech.progressive.repository.AttendanceRepository;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.repository.StudentRepository;
import com.edutech.progressive.repository.UserRepository;
import com.edutech.progressive.service.StudentService;

@Service
public class StudentServiceImplJpa implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public StudentServiceImplJpa() {
    }

    public StudentServiceImplJpa(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
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
    @Transactional
    public void deleteStudent(int studentId) {
        Student student = studentRepository.findByStudentId(studentId);

        if (student == null) {
            throw new RuntimeException("Student not found with ID: " + studentId);
        }

        /*
         * Correct delete order:
         * 1. Delete attendance rows linked with student
         * 2. Delete enrollment rows linked with student
         * 3. Delete user row linked with student
         * 4. Delete student row
         */

        if (attendanceRepository != null) {
            attendanceRepository.deleteByStudent_StudentId(studentId);
        }

        if (enrollmentRepository != null) {
            enrollmentRepository.deleteByStudent_StudentId(studentId);
        }

        if (userRepository != null) {
            userRepository.deleteByStudentId(studentId);
        }

        studentRepository.deleteById(studentId);
    }

    @Override
    public Student getStudentById(int studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    @Transactional
    public void modifyStudentDetails(StudentDTO studentDTO) {
        Student student = studentRepository.findByStudentId(studentDTO.getStudentId());

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        Student existingStudent = studentRepository.findByEmail(studentDTO.getEmail());

        if (existingStudent != null && existingStudent.getStudentId() != studentDTO.getStudentId()) {
            throw new StudentAlreadyExistsException("Another student with this email already exists");
        }

        User user = userRepository.findByStudentId(studentDTO.getStudentId());

        if (user == null) {
            throw new RuntimeException("Associated user not found");
        }

        User sameUsernameUser = userRepository.findByUsername(studentDTO.getUsername());

        if (sameUsernameUser != null && sameUsernameUser.getUserId() != user.getUserId()) {
            throw new RuntimeException("Username already exists");
        }

        student.setFullName(studentDTO.getFullName());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        student.setContactNumber(studentDTO.getContactNumber());
        student.setEmail(studentDTO.getEmail());
        student.setAddress(studentDTO.getAddress());

        studentRepository.save(student);

        user.setUsername(studentDTO.getUsername());

        if (studentDTO.getPassword() != null && !studentDTO.getPassword().trim().isEmpty()) {
            user.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
        }

        user.setStudentId(student.getStudentId());
        user.setReferenceId(student.getStudentId());

        userRepository.save(user);
    }
}