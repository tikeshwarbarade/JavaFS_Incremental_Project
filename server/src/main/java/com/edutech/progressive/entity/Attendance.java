package com.edutech.progressive.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
    name = "attendance",
    uniqueConstraints = @UniqueConstraint(columnNames = {"course_id", "student_id", "attendance_date"})
)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private int attendanceId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Temporal(TemporalType.DATE)
    @Column(name = "attendance_date", nullable = false)
    private Date attendanceDate;

    @Column(name = "status", nullable = false)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false, insertable = false)
    private Date createdAt;

    public Attendance() {
    }

    public Attendance(Course course, Student student, Date attendanceDate, String status) {
        this.course = course;
        this.student = student;
        this.attendanceDate = attendanceDate;
        this.status = status;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
