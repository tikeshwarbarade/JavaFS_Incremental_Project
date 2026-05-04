import { Component, OnInit } from '@angular/core';

import { EduconnectService } from '../../services/educonnect.service';
import { Teacher } from '../../models/Teacher';
import { Course } from '../../models/Course';
import { Student } from '../../models/Student';
import { Enrollment } from '../../models/Enrollment';
import { User } from '../../models/User';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  teacher: Teacher | null = null;
  courses: Course[] = [];
  students: Student[] = [];
  enrolledStudents: Student[] = [];
  selectedCourseId: number | null = null;
  errorMessage: string | null = null;
  role: string | null = null;

  constructor(private educonnectService: EduconnectService) {}

  ngOnInit(): void {
    this.role = localStorage.getItem('role');

    if (this.role !== 'TEACHER') {
      this.errorMessage = 'Only teachers can access this dashboard.';
      return;
    }

    this.loadTeacherDashboard();
    this.loadAllStudents();
  }

  private loadTeacherDashboard(): void {
    const teacherId = localStorage.getItem('teacherId');
    const userId = localStorage.getItem('userId');

    if (teacherId) {
      this.fetchTeacherData(+teacherId);
      return;
    }

    if (userId) {
      this.educonnectService.getUserById(+userId).subscribe({
        next: (user: User) => {
          if (user.teacher) {
            this.teacher = user.teacher;
            this.fetchCourses(user.teacher.teacherId);
          } else {
            this.errorMessage = 'Teacher information not found.';
          }
        },
        error: () => {
          this.errorMessage = 'Failed to load user details.';
        }
      });
      return;
    }

    this.errorMessage = 'Teacher session information not found.';
  }

  private fetchTeacherData(teacherId: number): void {
    this.educonnectService.getTeacherById(teacherId).subscribe({
      next: (teacher: Teacher) => {
        this.teacher = teacher;
        this.fetchCourses(teacher.teacherId);
      },
      error: () => {
        this.errorMessage = 'Failed to load teacher details.';
      }
    });
  }

  private fetchCourses(teacherId: number): void {
    this.educonnectService.getCoursesByTeacherId(teacherId).subscribe({
      next: (courses: Course[]) => {
        this.courses = courses;
      },
      error: () => {
        this.errorMessage = 'Failed to load teacher courses.';
      }
    });
  }

  private loadAllStudents(): void {
    this.educonnectService.getAllStudents().subscribe({
      next: (students: Student[]) => {
        this.students = students;
      },
      error: () => {
        this.errorMessage = 'Failed to load students.';
      }
    });
  }

  onCourseSelect(courseId: string): void {
    this.selectedCourseId = courseId ? +courseId : null;
    this.enrolledStudents = [];

    if (!this.selectedCourseId) {
      return;
    }

    this.educonnectService.getEnrollmentsByCourse(this.selectedCourseId).subscribe({
      next: (enrollments: Enrollment[]) => {
        this.enrolledStudents = enrollments.map((enrollment) => enrollment.student);
      },
      error: () => {
        this.errorMessage = 'Failed to load enrolled students.';
      }
    });
  }
}