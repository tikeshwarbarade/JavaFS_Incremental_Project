package com.edutech.progressive.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "reference_id")
    private Integer referenceId;

    @Transient
    private String token;

    @Transient
    private String roles;

    @Transient
    private Student student;

    @Transient
    private Teacher teacher;

    public User() {
    }

    public User(int userId, String username, String password, String role, Student student, Teacher teacher) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.student = student;
        this.teacher = teacher;

        if (student != null) {
            this.referenceId = student.getStudentId();
        } else if (teacher != null) {
            this.referenceId = teacher.getTeacherId();
        }
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // persisted role
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }

    // helper/token fields
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoles() {
        return roles != null ? roles : role;
    }

    public void setRoles(String roles) {
        this.roles = roles;
        this.role = roles;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
        if (student != null) {
            this.referenceId = student.getStudentId();
        }
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
        if (teacher != null) {
            this.referenceId = teacher.getTeacherId();
        }
    }
}