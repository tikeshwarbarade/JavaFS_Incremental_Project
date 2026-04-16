package com.edutech.progressive.service;

import com.edutech.progressive.entity.Enrollment;
import java.util.List;

public interface EnrollmentService {

    List<Enrollment> getAllEnrollments();

    int createEnrollment(Enrollment enrollment);

    void updateEnrollment(Enrollment enrollment);

    Enrollment getEnrollmentById(int enrollmentId);

    List<Enrollment> getAllEnrollmentsByStudent(int studentId);

    List<Enrollment> getAllEnrollmentsByCourse(int courseId);
}