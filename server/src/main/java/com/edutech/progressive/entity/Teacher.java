package com.edutech.progressive.entity;

public class Teacher implements Comparable<Teacher> {

    private int teacherId;
    private String fullName;
    private String subject;
    private String contactNumber;
    private String email;
    private int yearsOfExperience;
    public Teacher() {
    }
    public Teacher(int teacherId, String fullName, String subject, String contactNumber, String email,
            int yearsOfExperience) {
        this.teacherId = teacherId;
        this.fullName = fullName;
        this.subject = subject;
        this.contactNumber = contactNumber;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
    }
    public int getTeacherId() {
        return teacherId;
    }
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }
    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    @Override
    public int compareTo(Teacher o) {
    return Integer.compare(this.getYearsOfExperience(), o.getYearsOfExperience());
    }
    
    
}