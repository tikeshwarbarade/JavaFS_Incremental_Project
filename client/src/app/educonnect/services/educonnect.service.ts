import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Student } from '../models/Student';
import { Teacher } from '../models/Teacher';
import { Course } from '../models/Course';
import { Enrollment } from '../models/Enrollment';
import { User } from '../models/User';

export interface StudentDTO {
  studentId: number;
  username?: string;
  password?: string;
  fullName: string;
  dateOfBirth: Date | string;
  contactNumber: string;
  email: string;
  address: string;
}

export interface TeacherDTO {
  teacherId?: number;
  username?: string;
  password?: string;
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
  private baseUrl: string = this.getApiBaseUrl();

  constructor(private http: HttpClient) {}

  private getApiBaseUrl(): string {
    const origin = window.location.origin;
    const pathname = window.location.pathname;

    const proxyMatch = pathname.match(/^(.*\/proxy\/)5000\//);

    if (proxyMatch) {
      return `${origin}${proxyMatch[1]}3000/`;
    }

    return 'http://localhost:3000/';
  }

  private getHttpOptions(): { headers: HttpHeaders } {
    const token: string | null = localStorage.getItem('token');

    let headers: HttpHeaders = new HttpHeaders({
      'Content-Type': 'application/json'
    });

    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    return { headers };
  }

  // -------------------- Student APIs --------------------
  addStudent(student: Student): Observable<Student> {
    return this.http.post<Student>(
      `${this.baseUrl}student`,
      student,
      this.getHttpOptions()
    );
  }

  updateStudent(student: StudentDTO): Observable<Student> {
    return this.http.put<Student>(
      `${this.baseUrl}student/${student.studentId}`,
      student,
      this.getHttpOptions()
    );
  }

  deleteStudent(studentId: number): Observable<any> {
    return this.http.delete<any>(
      `${this.baseUrl}student/${studentId}`,
      this.getHttpOptions()
    );
  }

  getAllStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(
      `${this.baseUrl}student`,
      this.getHttpOptions()
    );
  }

  getStudentById(studentId: number): Observable<Student> {
    return this.http.get<Student>(
      `${this.baseUrl}student/${studentId}`,
      this.getHttpOptions()
    );
  }

  getStudentByld(studentId: number): Observable<Student> {
    return this.getStudentById(studentId);
  }

  // -------------------- Teacher APIs --------------------
  addTeacher(teacher: Teacher): Observable<Teacher> {
    return this.http.post<Teacher>(
      `${this.baseUrl}teacher`,
      teacher,
      this.getHttpOptions()
    );
  }

  updateTeacher(teacher: TeacherDTO): Observable<Teacher> {
    const teacherId = teacher.teacherId || 0;
    return this.http.put<Teacher>(
      `${this.baseUrl}teacher/${teacherId}`,
      teacher,
      this.getHttpOptions()
    );
  }

  deleteTeacher(teacherId: number): Observable<any> {
    return this.http.delete<any>(
      `${this.baseUrl}teacher/${teacherId}`,
      this.getHttpOptions()
    );
  }

  getAllTeachers(): Observable<Teacher[]> {
    return this.http.get<Teacher[]>(
      `${this.baseUrl}teacher`,
      this.getHttpOptions()
    );
  }

  getAlITeachers(): Observable<Teacher[]> {
    return this.getAllTeachers();
  }

  getTeacherById(teacherId: number): Observable<Teacher> {
    return this.http.get<Teacher>(
      `${this.baseUrl}teacher/${teacherId}`,
      this.getHttpOptions()
    );
  }

  getTeacherByld(teacherId: number): Observable<Teacher> {
    return this.getTeacherById(teacherId);
  }

  // -------------------- Course APIs --------------------
  addCourse(course: any): Observable<any> {
    return this.http.post<any>(
      `${this.baseUrl}course`,
      course,
      this.getHttpOptions()
    );
  }

  updateCourse(course: any): Observable<any> {
    return this.http.put<any>(
      `${this.baseUrl}course/${course.courseId}`,
      course,
      this.getHttpOptions()
    );
  }

  deleteCourse(courseId: number): Observable<any> {
    return this.http.delete<any>(
      `${this.baseUrl}course/${courseId}`,
      this.getHttpOptions()
    );
  }

  getAllCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(
      `${this.baseUrl}course`,
      this.getHttpOptions()
    );
  }

  getCourseById(courseId: number): Observable<Course> {
    return this.http.get<Course>(
      `${this.baseUrl}course/${courseId}`,
      this.getHttpOptions()
    );
  }

  getCourseByld(courseId: number): Observable<Course> {
    return this.getCourseById(courseId);
  }

  getAllCourseByTeacherId(teacherId: number): Observable<Course[]> {
    return this.http.get<Course[]>(
      `${this.baseUrl}course/teacher/${teacherId}`,
      this.getHttpOptions()
    );
  }

  getCoursesByTeacherId(teacherId: number): Observable<Course[]> {
    return this.getAllCourseByTeacherId(teacherId);
  }

  getCoursesByTeacherld(teacherId: number): Observable<Course[]> {
    return this.getAllCourseByTeacherId(teacherId);
  }

  // -------------------- Enrollment APIs --------------------
  createEnrollment(enrollment: any): Observable<any> {
    return this.http.post<any>(
      `${this.baseUrl}enrollment`,
      enrollment,
      this.getHttpOptions()
    );
  }

  updateEnrollment(enrollment: any): Observable<any> {
    return this.http.put<any>(
      `${this.baseUrl}enrollment/${enrollment.enrollmentId}`,
      enrollment,
      this.getHttpOptions()
    );
  }

  deleteEnrollment(enrollmentId: number): Observable<any> {
    return this.http.delete<any>(
      `${this.baseUrl}enrollment/${enrollmentId}`,
      this.getHttpOptions()
    );
  }

  getAllEnrollments(): Observable<Enrollment[]> {
    return this.http.get<Enrollment[]>(
      `${this.baseUrl}enrollment`,
      this.getHttpOptions()
    );
  }

  getEnrollmentById(enrollmentId: number): Observable<Enrollment> {
    return this.http.get<Enrollment>(
      `${this.baseUrl}enrollment/${enrollmentId}`,
      this.getHttpOptions()
    );
  }

  getEnrollmentByld(enrollmentId: number): Observable<Enrollment> {
    return this.getEnrollmentById(enrollmentId);
  }

  getEnrollmentsByStudent(studentId: number): Observable<Enrollment[]> {
    return this.http.get<Enrollment[]>(
      `${this.baseUrl}enrollment/student/${studentId}`,
      this.getHttpOptions()
    );
  }

  getAllEnrollmentsByStudent(studentId: number): Observable<Enrollment[]> {
    return this.getEnrollmentsByStudent(studentId);
  }

  getEnrollmentsByCourse(courseId: number): Observable<Enrollment[]> {
    return this.http.get<Enrollment[]>(
      `${this.baseUrl}enrollment/course/${courseId}`,
      this.getHttpOptions()
    );
  }

  getAllEnrollmentsByCourse(courseId: number): Observable<Enrollment[]> {
    return this.getEnrollmentsByCourse(courseId);
  }

  // -------------------- User APIs --------------------
  getUserById(userId: number): Observable<User> {
    return this.http.get<User>(
      `${this.baseUrl}user/${userId}`,
      this.getHttpOptions()
    );
  }

  getUserByld(userId: number): Observable<User> {
    return this.getUserById(userId);
  }
}

export { EduConnectService as EduconnectService };
