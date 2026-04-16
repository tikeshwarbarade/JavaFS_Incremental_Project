package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Attendance;
import com.edutech.progressive.repository.AttendanceRepository;
import com.edutech.progressive.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public AttendanceServiceImpl() {
    }

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @Override
    public Attendance createAttendance(Attendance attendance) {

        boolean exists = attendanceRepository
                .findByCourse_CourseIdAndStudent_StudentIdAndAttendanceDate(
                        attendance.getCourse().getCourseId(),
                        attendance.getStudent().getStudentId(),
                        attendance.getAttendanceDate()
                )
                .isPresent();

        if (exists) {
            throw new RuntimeException("Attendance already recorded for this student on this date");
        }

        attendance.setAttendanceDate(
                attendance.getAttendanceDate() == null ? new Date() : attendance.getAttendanceDate()
        );

        return attendanceRepository.save(attendance);
    }

    @Override
    public void deleteAttendance(int attendanceId) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance record not found"));

        attendanceRepository.delete(attendance);
    }

    @Override
    public List<Attendance> getAttendanceByStudent(int studentId) {
        return attendanceRepository.findByStudent_StudentId(studentId);
    }

    @Override
    public List<Attendance> getAttendanceByCourse(int courseId) {
        return attendanceRepository.findByCourse_CourseId(courseId);
    }
}