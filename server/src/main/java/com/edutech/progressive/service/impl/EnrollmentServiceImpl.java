package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Enrollment;
import com.edutech.progressive.repository.EnrollmentRepository;
import com.edutech.progressive.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // Used by tests
    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    // Used by Spring
    public EnrollmentServiceImpl() {
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public int createEnrollment(Enrollment enrollment) {
        boolean alreadyExists = enrollmentRepository
                .findByStudent_StudentIdAndCourse_CourseId(
                        enrollment.getStudent().getStudentId(),
                        enrollment.getCourse().getCourseId()
                )
                .isPresent();

        if (alreadyExists) {
            throw new RuntimeException("Student is already enrolled in this course");
        }

        enrollment.setEnrollmentDate(new Date());

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        return savedEnrollment.getEnrollmentId();
    }

    @Override
    public void updateEnrollment(Enrollment updatedEnrollment) {
        Enrollment existingEnrollment = enrollmentRepository
                .findById(updatedEnrollment.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        existingEnrollment.setStudent(updatedEnrollment.getStudent());
        existingEnrollment.setCourse(updatedEnrollment.getCourse());
        existingEnrollment.setEnrollmentDate(updatedEnrollment.getEnrollmentDate());

        enrollmentRepository.save(existingEnrollment);
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