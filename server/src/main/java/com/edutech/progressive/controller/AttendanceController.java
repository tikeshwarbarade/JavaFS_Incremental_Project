package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Attendance;
import com.edutech.progressive.service.impl.AttendanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceServiceImpl attendanceServiceImpl;

    // GET /attendance
    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendance() {
        try {
            return ResponseEntity.ok(attendanceServiceImpl.getAllAttendance());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // POST /attendance
    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        try {
            Attendance saved = attendanceServiceImpl.createAttendance(attendance);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // DELETE /attendance/{attendanceId}
    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable int attendanceId) {
        try {
            attendanceServiceImpl.deleteAttendance(attendanceId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /attendance/student/{studentId}
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(@PathVariable int studentId) {
        try {
            return ResponseEntity.ok(attendanceServiceImpl.getAttendanceByStudent(studentId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /attendance/course/{courseId}
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Attendance>> getAttendanceByCourse(@PathVariable int courseId) {
        try {
            return ResponseEntity.ok(attendanceServiceImpl.getAttendanceByCourse(courseId));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}