import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { EduconnectService } from '../../services/educonnect.service';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  role: string = '';

  teacherId: number | null = null;
  teacherDetails: any;
  students: any[] = [];
  teacherEnrollments: any[] = [];

  studentId: number | null = null;
  studentDetails: any;
  enrollments: any[] = [];
  courses: any[] = [];

  successMessage: string | null = null;
  errorMessage: string | null = null;

  showDeleteModal: boolean = false;
  deleteTarget: 'student' | 'teacher' | null = null;
  deleteModalTitle: string = '';
  deleteModalMessage: string = '';

  constructor(
    private educonnectService: EduconnectService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const auth: any = this.authService;

    this.role = auth.getRole ? auth.getRole() : '';

    if (!this.role) {
      this.errorMessage = 'Please login to view your dashboard.';
      return;
    }

    if (this.role === 'STUDENT') {
      this.studentId = auth.getStudentId ? auth.getStudentId() : null;

      if (this.studentId !== null && this.studentId !== 0) {
        this.loadStudentData();
      } else {
        this.loadFallbackStudentView();
      }
    }

    if (this.role === 'TEACHER') {
      this.teacherId = auth.getTeacherId ? auth.getTeacherId() : null;

      if (this.teacherId !== null && this.teacherId !== 0) {
        this.loadTeacherData();
      } else {
        this.loadFallbackTeacherView();
      }
    }
  }

  loadFallbackStudentView(): void {
    this.studentDetails = {
      studentId: 0,
      fullName: 'Demo Student',
      email: 'demo.student@example.com',
      dateOfBirth: '2026-05-10',
      contactNumber: '1234567890',
      address: 'Demo Address'
    };

    this.courses = [];
    this.enrollments = [];
    this.errorMessage = 'API failed. Showing fallback student details.';
  }

  loadFallbackTeacherView(): void {
    this.teacherDetails = {
      teacherId: 0,
      fullName: 'Demo Teacher',
      email: 'demo.teacher@example.com',
      contactNumber: '1234567890',
      subject: 'Computer',
      yearsOfExperience: 15
    };

    this.courses = [];
    this.students = [];
    this.teacherEnrollments = [];
    this.errorMessage = 'API failed. Showing fallback teacher details.';
  }

  loadStudentData(): void {
    if (this.studentId === null) {
      return;
    }

    const service: any = this.educonnectService;

    this.successMessage = null;
    this.errorMessage = null;

    if (service.getStudentById) {
      service.getStudentById(this.studentId).subscribe({
        next: (data: any) => {
          this.studentDetails = data;
          localStorage.setItem('currentStudent', JSON.stringify(data));
        },
        error: (error: any) => {
          console.error('Failed to load student details:', error);
          this.loadFallbackStudentView();
        }
      });
    }

    if (service.getEnrollmentsByStudent) {
      service.getEnrollmentsByStudent(this.studentId).subscribe({
        next: (data: any) => {
          this.enrollments = data || [];
        },
        error: (error: any) => {
          console.error('Failed to load student enrollments:', error);
          this.enrollments = [];
        }
      });
    }

    if (service.getAllCourses) {
      service.getAllCourses().subscribe({
        next: (data: any) => {
          this.courses = data || [];
          localStorage.setItem('allCourses', JSON.stringify(this.courses));
        },
        error: (error: any) => {
          console.error('Failed to load courses:', error);
          this.courses = [];
        }
      });
    }
  }

  loadTeacherData(): void {
    if (this.teacherId === null) {
      return;
    }

    const service: any = this.educonnectService;

    this.successMessage = null;
    this.errorMessage = null;

    if (service.getTeacherById) {
      service.getTeacherById(this.teacherId).subscribe({
        next: (data: any) => {
          this.teacherDetails = data;
          localStorage.setItem('currentTeacher', JSON.stringify(data));
        },
        error: (error: any) => {
          console.error('Failed to load teacher details:', error);
          this.loadFallbackTeacherView();
        }
      });
    }

    if (service.getAllCourseByTeacherId) {
      service.getAllCourseByTeacherId(this.teacherId).subscribe({
        next: (data: any) => {
          this.courses = data || [];
          localStorage.setItem('allCourses', JSON.stringify(this.courses));
        },
        error: (error: any) => {
          console.error('Failed to load teacher courses:', error);
          this.courses = [];
        }
      });
    }

    if (service.getAllStudents) {
      service.getAllStudents().subscribe({
        next: (data: any) => {
          this.students = data || [];
        },
        error: (error: any) => {
          console.error('Failed to load students:', error);
          this.students = [];
        }
      });
    }

    if (service.getAllEnrollments) {
      service.getAllEnrollments().subscribe({
        next: (data: any) => {
          this.teacherEnrollments = data || [];
        },
        error: (error: any) => {
          console.error('Failed to load enrollments:', error);
          this.teacherEnrollments = [];
        }
      });
    }
  }

  editProfile(): void {
    if (this.role === 'STUDENT') {
      this.router.navigate(['/educonnect/student-edit']);
    } else if (this.role === 'TEACHER') {
      this.router.navigate(['/educonnect/teacher-edit']);
    }
  }

  editCourse(course: any): void {
    this.router.navigate(['/educonnect/course-edit', course.courseId]);
  }

  goToCourseCreate(): void {
    this.router.navigate(['/educonnect/course']);
  }

  goToEnrollment(): void {
    this.router.navigate(['/educonnect/enrollment']);
  }

  deleteProfile(): void {
    if (this.role === 'STUDENT') {
      this.openDeleteModal('student');
    } else if (this.role === 'TEACHER') {
      this.openDeleteModal('teacher');
    }
  }

  openDeleteModal(target: 'student' | 'teacher'): void {
    this.deleteTarget = target;
    this.showDeleteModal = true;

    if (target === 'student') {
      this.deleteModalTitle = 'Delete Student Account';
      this.deleteModalMessage =
        'Are you sure you want to delete your student account? This will remove your login account and related student data.';
    }

    if (target === 'teacher') {
      this.deleteModalTitle = 'Delete Teacher Account';
      this.deleteModalMessage =
        'Are you sure you want to delete your teacher account? This will remove your login account and related teacher data.';
    }
  }

  closeDeleteModal(): void {
    this.showDeleteModal = false;
    this.deleteTarget = null;
    this.deleteModalTitle = '';
    this.deleteModalMessage = '';
  }

  confirmDeleteAccount(): void {
    if (this.deleteTarget === 'student') {
      this.deleteStudent();
    }

    if (this.deleteTarget === 'teacher') {
      this.deleteTeacher();
    }
  }

  deleteStudent(): void {
    const service: any = this.educonnectService;

    this.successMessage = null;
    this.errorMessage = null;

    if (!this.studentId || this.studentId === 0) {
      this.closeDeleteModal();
      this.errorMessage = 'Cannot delete fallback student. Please login with a valid student.';
      return;
    }

    if (service.deleteStudent) {
      service.deleteStudent(this.studentId).subscribe({
        next: () => {
          this.closeDeleteModal();

          this.studentDetails = undefined;
          this.enrollments = [];
          this.courses = [];

          this.successMessage =
            'Student account deleted successfully. Please register again to continue.';
          this.errorMessage = null;

          setTimeout(() => {
            this.authService.logout();
            localStorage.clear();
            this.router.navigate(['/auth/register']);
          }, 1800);
        },
        error: (error: any) => {
          this.closeDeleteModal();

          console.error('Failed to delete student:', error);

          this.successMessage = null;
          this.errorMessage =
            error?.error?.message ||
            error?.error ||
            'Unable to delete student profile. Student may have enrollments or linked records.';
        }
      });
    }
  }

  deleteTeacher(): void {
    const service: any = this.educonnectService;

    this.successMessage = null;
    this.errorMessage = null;

    if (!this.teacherId || this.teacherId === 0) {
      this.closeDeleteModal();
      this.errorMessage = 'Cannot delete fallback teacher. Please login with a valid teacher.';
      return;
    }

    if (service.deleteTeacher) {
      service.deleteTeacher(this.teacherId).subscribe({
        next: () => {
          this.closeDeleteModal();

          this.teacherDetails = undefined;
          this.courses = [];

          this.successMessage =
            'Teacher account deleted successfully. Please register again to continue.';
          this.errorMessage = null;

          setTimeout(() => {
            this.authService.logout();
            localStorage.clear();
            this.router.navigate(['/auth/register']);
          }, 1800);
        },
        error: (error: any) => {
          this.closeDeleteModal();

          console.error('Failed to delete teacher:', error);

          this.successMessage = null;
          this.errorMessage =
            error?.error?.message ||
            error?.error ||
            'Unable to delete teacher profile. Teacher may have courses or linked records.';
        }
      });
    }
  }

  deleteCourse(courseId: number): void {
    const service: any = this.educonnectService;

    this.successMessage = null;
    this.errorMessage = null;

    if (service.deleteCourse) {
      service.deleteCourse(courseId).subscribe({
        next: () => {
          this.courses = this.courses.filter(course => course.courseId !== courseId);
          this.successMessage = 'Course deleted successfully!';
        },
        error: (error: any) => {
          console.error('Failed to delete course:', error);

          this.successMessage = null;
          this.errorMessage =
            error?.error?.message ||
            error?.error ||
            'Unable to delete course.';
        }
      });
    }
  }

  removeEnrollment(enrollmentId: number): void {
    const service: any = this.educonnectService;

    this.successMessage = null;
    this.errorMessage = null;

    if (service.deleteEnrollment) {
      service.deleteEnrollment(enrollmentId).subscribe({
        next: () => {
          this.teacherEnrollments = this.teacherEnrollments.filter(
            enrollment => enrollment.enrollmentId !== enrollmentId
          );

          this.successMessage = 'Enrollment removed successfully!';
          this.errorMessage = null;
        },
        error: (error: any) => {
          console.error('Failed to remove enrollment:', error);

          this.successMessage = null;
          this.errorMessage =
            error?.error?.message ||
            error?.error ||
            'Unable to remove enrollment.';
        }
      });
    }
  }

  trackByCourseId(index: number, item: any): number {
    return item.courseId;
  }

  trackByEnrollmentId(index: number, item: any): number {
    return item.enrollmentId;
  }

  trackByStudentId(index: number, item: any): number {
    return item.studentId;
  }
}