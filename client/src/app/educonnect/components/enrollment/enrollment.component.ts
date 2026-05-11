import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { EduconnectService } from '../../services/educonnect.service';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-enrollment',
  templateUrl: './enrollment.component.html',
  styleUrls: ['./enrollment.component.scss']
})
export class EnrollmentComponent implements OnInit {
  enrollmentForm!: FormGroup;

  availableCourses: any[] = [];
  students: any[] = [];

  successMessage: string | null = null;
  errorMessage: string | null = null;

  role: string = '';
  studentId: number = 0;
  currentStudentName: string = '';

  constructor(
    private fb: FormBuilder,
    private service: EduconnectService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const auth: any = this.authService;

    this.role = auth.getRole ? auth.getRole() : '';
    this.studentId = auth.getStudentId ? auth.getStudentId() : 0;

    this.enrollmentForm = this.fb.group({
      courseId: ['', Validators.required],
      studentId: [
        this.role === 'STUDENT' && this.studentId ? this.studentId : '',
        Validators.required
      ],
      enrollmentDate: ['', Validators.required]
    });

    this.loadCourses();
    this.loadStudentsBasedOnRole();
  }

  loadCourses(): void {
    this.service.getAllCourses().subscribe({
      next: (courses: any[]) => {
        this.availableCourses = courses || [];
        localStorage.setItem('allCourses', JSON.stringify(this.availableCourses));
      },
      error: (error: any) => {
        console.error('Failed to load courses:', error);
        this.availableCourses = [];
      }
    });
  }

  loadStudentsBasedOnRole(): void {
    if (this.role === 'STUDENT') {
      if (!this.studentId) {
        this.errorMessage = 'Student ID not found. Please login again.';
        return;
      }

      this.service.getStudentById(this.studentId).subscribe({
        next: (student: any) => {
          this.students = [student];
          this.currentStudentName = student.fullName;

          this.enrollmentForm.patchValue({
            studentId: student.studentId
          });

          this.enrollmentForm.markAsPristine();
          this.enrollmentForm.markAsUntouched();
        },
        error: (error: any) => {
          console.error('Failed to load logged-in student:', error);
          this.students = [];
          this.errorMessage = 'Unable to load logged-in student details.';
        }
      });

      return;
    }

    if (this.role === 'TEACHER') {
      this.service.getAllStudents().subscribe({
        next: (students: any[]) => {
          this.students = students || [];
        },
        error: (error: any) => {
          console.error('Failed to load students:', error);
          this.students = [];
        }
      });
    }
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.enrollmentForm.invalid) {
      this.enrollmentForm.markAllAsTouched();
      this.errorMessage = 'Please fill out all fields correctly.';
      return;
    }

    const formValue = this.enrollmentForm.value;

    const finalStudentId =
      this.role === 'STUDENT'
        ? this.studentId
        : Number(formValue.studentId);

    const enrollmentPayload: any = {
      enrollmentId: 0,
      course: {
        courseId: Number(formValue.courseId)
      },
      student: {
        studentId: Number(finalStudentId)
      },
      enrollmentDate: formValue.enrollmentDate
    };

    this.service.createEnrollment(enrollmentPayload).subscribe({
      next: () => {
        this.successMessage = 'Enrollment created successfully!';
        this.errorMessage = null;

        this.resetFormAfterSuccess();
      },
      error: (error: any) => {
        console.error('Failed to create enrollment:', error);

        this.successMessage = null;
        this.errorMessage =
          error?.error?.message ||
          error?.error ||
          'Failed to create enrollment.';
      }
    });
  }

  resetForm(): void {
    this.enrollmentForm.reset({
      courseId: '',
      studentId: this.role === 'STUDENT' && this.studentId ? this.studentId : '',
      enrollmentDate: ''
    });

    this.successMessage = null;
    this.errorMessage = null;

    this.enrollmentForm.markAsPristine();
    this.enrollmentForm.markAsUntouched();
    this.enrollmentForm.updateValueAndValidity();
  }

  resetFormAfterSuccess(): void {
    this.enrollmentForm.reset({
      courseId: '',
      studentId: this.role === 'STUDENT' && this.studentId ? this.studentId : '',
      enrollmentDate: ''
    });

    this.enrollmentForm.markAsPristine();
    this.enrollmentForm.markAsUntouched();
    this.enrollmentForm.updateValueAndValidity();
  }

  goToDashboard(): void {
    this.router.navigate(['/educonnect/dashboard']);
  }
}