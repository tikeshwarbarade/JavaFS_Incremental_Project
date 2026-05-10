package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Course;
import com.edutech.progressive.entity.Enrollment;
import com.edutech.progressive.entity.Student;
import com.edutech.progressive.repository.CourseRepository;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.repository.StudentRepository;
import com.edutech.progressive.service.EnrollmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    public EnrollmentServiceImpl() {
    }

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    @Transactional
    public int createEnrollment(Enrollment enrollment) {
        if (enrollment.getStudent() == null || enrollment.getCourse() == null) {
            throw new RuntimeException("Student and course are required");
        }

        int studentId = enrollment.getStudent().getStudentId();
        int courseId = enrollment.getCourse().getCourseId();

        boolean alreadyExists = enrollmentRepository
                .findByStudent_StudentIdAndCourse_CourseId(studentId, courseId)
                .isPresent();

        if (alreadyExists) {
            throw new RuntimeException("Student is already enrolled in this course");
        }

        Student student = studentRepository.findByStudentId(studentId);

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        Course course = courseRepository.findByCourseId(courseId);

        if (course == null) {
            throw new RuntimeException("Course not found");
        }

        enrollment.setStudent(student);
        enrollment.setCourse(course);

        if (enrollment.getEnrollmentDate() == null) {
            enrollment.setEnrollmentDate(new Date());
        }

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return savedEnrollment.getEnrollmentId();
    }

    @Override
    @Transactional
    public void updateEnrollment(Enrollment updatedEnrollment) {
        Enrollment existingEnrollment = enrollmentRepository
                .findById(updatedEnrollment.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (updatedEnrollment.getStudent() != null) {
            Student student = studentRepository.findByStudentId(updatedEnrollment.getStudent().getStudentId());

            if (student == null) {
                throw new RuntimeException("Student not found");
            }

            existingEnrollment.setStudent(student);
        }

        if (updatedEnrollment.getCourse() != null) {
            Course course = courseRepository.findByCourseId(updatedEnrollment.getCourse().getCourseId());

            if (course == null) {
                throw new RuntimeException("Course not found");
            }

            existingEnrollment.setCourse(course);
        }

        existingEnrollment.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());
        enrollmentRepository.save(existingEnrollment);
    }

    @Transactional
    public void deleteEnrollment(int enrollmentId) {
        Enrollment existingEnrollment = enrollmentRepository
                .findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollmentRepository.delete(existingEnrollment);
    }

    @Override
    public Enrollment getEnrollmentById(int enrollmentId) {
        return enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
    }

    @Override
    public List<Enrollment> getAllEnrollmentsByStudent(int studentId) {
        return enrollmentRepository.findAllByStudent_StudentId(studentId);
    }

    @Override
    public List<Enrollment> getAllEnrollmentsByCourse(int courseId) {
        return enrollmentRepository.findAllByCourse_CourseId(courseId);
    }
}