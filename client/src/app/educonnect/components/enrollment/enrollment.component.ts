import { Component } from '@angular/core';

@Component({
  templateUrl: './enrollment.component.html',
  styleUrls: ['./enrollment.component.scss']
})
export class EnrollmentComponent {
  availableCourses: any[] = [];
  enrolledCourseIds: number[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor() {
    const coursesRaw = localStorage.getItem('allCourses');
    this.availableCourses = coursesRaw ? JSON.parse(coursesRaw) : [];

    const enrollmentsRaw = localStorage.getItem('currentEnrollments');
    this.enrolledCourseIds = enrollmentsRaw ? JSON.parse(enrollmentsRaw) : [];
  }

  isEnrolled(courseId: number): boolean {
    return this.enrolledCourseIds.includes(courseId);
  }

  enroll(course: any): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.isEnrolled(course.courseId)) {
      this.errorMessage = 'You are already enrolled in this course.';
      return;
    }

    this.enrolledCourseIds.push(course.courseId);
    localStorage.setItem('currentEnrollments', JSON.stringify(this.enrolledCourseIds));
    this.successMessage = `Successfully enrolled in ${course.courseName}.`;
  }
}
