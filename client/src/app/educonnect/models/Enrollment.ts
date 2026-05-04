import { Student } from './Student';
import { Course } from './Course';

export class Enrollment {
  enrollmentId: number;
  student: Student;
  course: Course;
  enrollmentDate: Date;

  constructor(
    enrollmentId: number,
    student: Student,
    course: Course,
    enrollmentDate: Date
  ) {
    this.enrollmentId = enrollmentId;
    this.student = student;
    this.course = course;
    this.enrollmentDate = enrollmentDate;
  }

  logAttributes(): void {
    console.log('enrollmentId:', this.enrollmentId);
    console.log('student:', this.student);
    console.log('course:', this.course);
    console.log('enrollmentDate:', this.enrollmentDate);
  }
}