package com.edutech.progressive.entity;

import java.util.Date;

public class Student implements Comparable<Student> {
  private int studentId;
  private String fullName;
  private Date dateOfBirth;
  private String contactNumber;
  private String email;
  private String address;
  public Student() {
  }
  public Student(int studentId, String fullName, Date dateOfBirth, String contactNumber, String email, String address) {
    this.studentId = studentId;
    this.fullName = fullName;
    this.dateOfBirth = dateOfBirth;
    this.contactNumber = contactNumber;
    this.email = email;
    this.address = address;
  }
  public int getStudentId() {
    return studentId;
  }
  public void setStudentId(int studentId) {
    this.studentId = studentId;
  }
  public String getFullName() {
    return fullName;
  }
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  public Date getDateOfBirth() {
    return dateOfBirth;
  }
  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
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
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  @Override
  public int compareTo(Student o) {
   return this.getFullName().compareTo(o.getFullName());
  }
  
}