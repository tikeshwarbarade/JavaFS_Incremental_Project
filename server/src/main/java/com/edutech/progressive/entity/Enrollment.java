package com.edutech.progressive.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
    name = "enrollment",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"})
)
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int enrollmentId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Temporal(TemporalType.DATE)
    private Date enrollmentDate;

    public Enrollment() {
    }

    public Enrollment(Student student, Course course, Date enrollmentDate) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
