import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Student } from '../models/Student';
import { Teacher } from '../models/Teacher';
import { Course } from '../models/Course';
import { Enrollment } from '../models/Enrollment';
import { User } from '../models/User';

export interface StudentDTO {
  studentId: number;
  fullName: string;
  dateOfBirth: Date;
  contactNumber: string;
  email: string;
  address: string;
}

export interface TeacherDTO {
  teacherId: number;
  fullName: string;
  contactNumber: string;
  email: string;
  subject: string;
  yearsOfExperience: number;
}

@Injectable({
  providedIn: 'root'
})
export class EduConnectService {
  private baseUrl: string = '';

  constructor(private http: HttpClient) {}

  // -------------------- Student APIs --------------------
  addStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(`${this.baseUrl}student`, student);
  }

  updateStudent(student: StudentDTO): Observable<Student> {
    return this.http.put<Student>(`${this.baseUrl}student`, student);
  }

  deleteStudent(studentId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}student/${studentId}`);
  }

  getAllStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(`${this.baseUrl}student`);
  }

  getStudentById(studentId: number): Observable<Student> {
    return this.http.get<Student>(`${this.baseUrl}student/${studentId}`);
  }

  getStudentByld(studentId: number): Observable<Student> {
    return this.getStudentById(studentId);
  }

  // -------------------- Teacher APIs --------------------
  addTeacher(teacher: Teacher): Observable<Teacher> {
    return this.http.post<Teacher>(`${this.baseUrl}teacher`, teacher);
  }

  updateTeacher(teacher: TeacherDTO): Observable<Teacher> {
    return this.http.put<Teacher>(`${this.baseUrl}teacher`, teacher);
  }

  deleteTeacher(teacherId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}teacher/${teacherId}`);
  }

  getAllTeachers(): Observable<Teacher[]> {
    return this.http.get<Teacher[]>(`${this.baseUrl}teacher`);
  }

  getAlITeachers(): Observable<Teacher[]> {
    return this.getAllTeachers();
  }

  getTeacherById(teacherId: number): Observable<Teacher> {
    return this.http.get<Teacher>(`${this.baseUrl}teacher/${teacherId}`);
  }

  getTeacherByld(teacherId: number): Observable<Teacher> {
    return this.getTeacherById(teacherId);
  }

  // -------------------- Course APIs --------------------
  addCourse(course: Course): Observable<Course> {
    return this.http.post<Course>(`${this.baseUrl}course`, course);
  }

  updateCourse(course: Course): Observable<Course> {
    return this.http.put<Course>(`${this.baseUrl}course`, course);
  }

  deleteCourse(courseId: number): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}course/${courseId}`);
  }

  getAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.baseUrl}course`);
  }

  getCourseById(courseId: number): Observable<Course> {
    return this.http.get<Course>(`${this.baseUrl}course/${courseId}`);
  }

  getCourseByld(courseId: number): Observable<Course> {
    return this.getCourseById(courseId);
  }

  getCoursesByTeacherId(teacherId: number): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.baseUrl}course/teacher/${teacherId}`);
  }

  getCoursesByTeacherld(teacherId: number): Observable<Course[]> {
    return this.getCoursesByTeacherId(teacherId);
  }

  // -------------------- Enrollment APIs --------------------
  createEnrollment(enrollment: Enrollment): Observable<Enrollment> {
    return this.http.post<Enrollment>(`${this.baseUrl}enrollment`, enrollment);
  }

  updateEnrollment(enrollment: Enrollment): Observable<Enrollment> {
    return this.http.put<Enrollment>(`${this.baseUrl}enrollment`, enrollment);
  }

  getAllEnrollments(): Observable<Enrollment[]> {
    return this.http.get<Enrollment[]>(`${this.baseUrl}enrollment`);
  }

  getEnrollmentById(enrollmentId: number): Observable<Enrollment> {
    return this.http.get<Enrollment>(`${this.baseUrl}enrollment/${enrollmentId}`);
  }

  getEnrollmentByld(enrollmentId: number): Observable<Enrollment> {
    return this.getEnrollmentById(enrollmentId);
  }

  getEnrollmentsByCourse(courseId: number): Observable<Enrollment[]> {
    return this.http.get<Enrollment[]>(`${this.baseUrl}enrollment/course/${courseId}`);
  }

  getEnrollmentsByStudent(studentId: number): Observable<Enrollment[]> {
    return this.http.get<Enrollment[]>(`${this.baseUrl}enrollment/student/${studentId}`);
  }

  // -------------------- User APIs --------------------
  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(`${this.baseUrl}user/${userId}`);
  }

  getUserByld(userId: number): Observable<User> {
    return this.getUserById(userId);
  }
}

// backward compatibility for your old imports
export { EduConnectService as EduconnectService };
