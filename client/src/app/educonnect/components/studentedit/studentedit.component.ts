import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { EduconnectService } from '../../services/educonnect.service';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-studentedit',
  templateUrl: './studentedit.component.html',
  styleUrls: ['./studentedit.component.scss']
})
export class StudentEditComponent implements OnInit {
  studentForm!: FormGroup;

  studentId: number = 1;
  userId: number = 1;

  student: any;
  user: any;

  originalUsername: string = '';

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private educonnectService: EduconnectService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const auth: any = this.authService;

    this.studentId = auth.getStudentId ? auth.getStudentId() : this.studentId;
    this.userId = auth.getUserId ? auth.getUserId() : this.userId;

    this.studentForm = this.fb.group({
      studentId: [this.studentId || 0],

      username: [
        '',
        [
          Validators.required,
          Validators.pattern(/^[a-zA-Z0-9]+$/)
        ]
      ],

      fullName: [
        '',
        [
          Validators.required,
          Validators.minLength(2)
        ]
      ],

      dateOfBirth: ['', Validators.required],

      contactNumber: [
        '',
        [
          Validators.required,
          Validators.pattern(/^\d{10}$/)
        ]
      ],

      email: [
        '',
        [
          Validators.required,
          Validators.email
        ]
      ],

      address: [
        '',
        [
          Validators.required,
          Validators.minLength(3)
        ]
      ]
    });

    this.loadStudentDetails();
  }

  loadStudentDetails(): void {
    const service: any = this.educonnectService;

    this.successMessage = null;
    this.errorMessage = null;

    if (!this.studentId || this.studentId === 0) {
      this.loadFallbackStudentEdit();
      return;
    }

    if (service.getStudentById) {
      service.getStudentById(this.studentId).subscribe({
        next: (student: any) => {
          this.student = student;

          this.studentForm.patchValue({
            studentId: student.studentId ?? student.studentld ?? this.studentId,
            fullName: student.fullName,
            dateOfBirth: this.formatDateForInput(student.dateOfBirth),
            contactNumber: student.contactNumber,
            email: student.email,
            address: student.address
          });
        },
        error: (error: any) => {
          console.error('Failed to load student details:', error);
          this.loadFallbackStudentEdit();
        }
      });
    }

    if (service.getUserById && this.userId) {
      service.getUserById(this.userId).subscribe({
        next: (user: any) => {
          this.user = user;
          this.originalUsername = user.username || '';

          this.studentForm.patchValue({
            username: user.username || ''
          });
        },
        error: (error: any) => {
          console.error('Failed to load user details:', error);

          this.studentForm.patchValue({
            username: ''
          });
        }
      });
    }
  }

  loadFallbackStudentEdit(): void {
    this.student = {
      studentId: 0,
      fullName: 'Demo Student',
      dateOfBirth: '2026-05-10',
      contactNumber: '1234567890',
      email: 'demo.student@example.com',
      address: 'Demo Address'
    };

    this.originalUsername = 'demoUser';

    this.studentForm.patchValue({
      studentId: 0,
      username: 'demoUser',
      fullName: this.student.fullName,
      dateOfBirth: this.formatDateForInput(this.student.dateOfBirth),
      contactNumber: this.student.contactNumber,
      email: this.student.email,
      address: this.student.address
    });

    this.errorMessage = 'API failed. Showing fallback student details.';
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.studentForm.invalid) {
      this.studentForm.markAllAsTouched();
      this.errorMessage = 'Please correct the highlighted fields before updating.';
      return;
    }

    if (!this.studentId || this.studentId === 0) {
      this.errorMessage = 'Cannot update fallback student. Please login with a valid student.';
      return;
    }

    const formValue = this.studentForm.value;

    const isUsernameChanged = formValue.username !== this.originalUsername;

    const payload: any = {
      studentId: this.studentId,
      username: formValue.username,

      /*
        Password is intentionally empty.
        Backend will not update password because password is empty.
        This prevents overwriting/double-encoding password.
      */
      password: '',

      fullName: formValue.fullName,
      dateOfBirth: formValue.dateOfBirth,
      contactNumber: formValue.contactNumber,
      email: formValue.email,
      address: formValue.address
    };

    console.log('Student update payload:', payload);

    const service: any = this.educonnectService;

    if (service.updateStudent) {
      service.updateStudent(payload).subscribe({
        next: () => {
          if (isUsernameChanged) {
            this.successMessage = 'Username updated successfully. Please login again.';
            this.errorMessage = null;

            setTimeout(() => {
              this.authService.logout();
              this.router.navigate(['/auth/login']);
            }, 1500);
          } else {
            this.successMessage = 'Student profile updated successfully!';
            this.errorMessage = null;

            setTimeout(() => {
              this.router.navigate(['/educonnect/dashboard']);
            }, 1000);
          }
        },
        error: (error: any) => {
          console.error('Failed to update student:', error);

          this.successMessage = null;
          this.errorMessage =
            error?.error?.message ||
            error?.error ||
            'Unable to update student profile.';
        }
      });
    }
  }

  resetForm(): void {
    this.loadStudentDetails();
    this.successMessage = null;
    this.errorMessage = null;
  }

  goToDashboard(): void {
    this.router.navigate(['/educonnect/dashboard']);
  }

  private formatDateForInput(dateValue: any): string {
    if (!dateValue) {
      return '';
    }

    return String(dateValue).substring(0, 10);
  }
}