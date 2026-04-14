package com.edutech.progressive.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;

    private String courseName;
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "teacher_id")
    @JsonBackReference
    private Teacher teacher;

    public Course() {
    }

    public Course(int courseId, String courseName, String description, int teacherId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.description = description;
        setTeacherId(teacherId);
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    // Compatibility helper for old code/tests
    @Transient
    public int getTeacherId() {
        return (teacher != null) ? teacher.getTeacherId() : 0;
    }

    // Compatibility helper for old code/tests
    public void setTeacherId(int teacherId) {
        if (this.teacher == null) {
            this.teacher = new Teacher();
        }
        this.teacher.setTeacherId(teacherId);
    }
}