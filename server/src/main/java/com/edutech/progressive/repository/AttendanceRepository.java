package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

    List<Attendance> findByStudent_StudentId(int studentId);

    List<Attendance> findByCourse_CourseId(int courseId);

    Optional<Attendance> findByCourse_CourseIdAndStudent_StudentIdAndAttendanceDate(
            int courseId, int studentId, Date attendanceDate
    );

    void deleteByCourse_CourseId(int courseId);

    void deleteByStudent_StudentId(int studentId);

    // ✅ Required for TeacherServiceImplJpa.deleteTeacher(...)
    void deleteByCourse_Teacher_TeacherId(int teacherId);
}