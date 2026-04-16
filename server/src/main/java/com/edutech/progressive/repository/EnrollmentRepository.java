package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

    Optional<Enrollment> findByStudent_StudentIdAndCourse_CourseId(int studentId, int courseId);

    List<Enrollment> findAllByStudent_StudentId(int studentId);

    List<Enrollment> findAllByCourse_CourseId(int courseId);

    void deleteByCourse_CourseId(int courseId);

    void deleteByStudent_StudentId(int studentId);

    void deleteByCourse_Teacher_TeacherId(int teacherId);
}