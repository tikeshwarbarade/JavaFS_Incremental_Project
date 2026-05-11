import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { EduconnectService } from '../../services/educonnect.service';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-teacheredit',
  templateUrl: './teacheredit.component.html',
  styleUrls: ['./teacheredit.component.scss']
})
export class TeacherEditComponent implements OnInit {
  teacherForm!: FormGroup;

  teacherId: number = 1;
  userId: number = 1;

  teacher: any;
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

    this.teacherId = auth.getTeacherId ? auth.getTeacherId() : this.teacherId;
    this.userId = auth.getUserId ? auth.getUserId() : this.userId;

    this.teacherForm = this.fb.group({
      teacherId: [this.teacherId || 0],

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

      subject: [
        '',
        [
          Validators.required,
          Validators.minLength(2)
        ]
      ],

      yearsOfExperience: [
        1,
        [
          Validators.required,
          Validators.min(1)
        ]
      ]
    });

    this.loadTeacherDetails();
  }

  loadTeacherDetails(): void {
    const service: any = this.educonnectService;

    this.successMessage = null;
    this.errorMessage = null;

    if (!this.teacherId || this.teacherId === 0) {
      this.loadFallbackTeacherEdit();
      return;
    }

    if (service.getTeacherById) {
      service.getTeacherById(this.teacherId).subscribe({
        next: (teacher: any) => {
          this.teacher = teacher;

          this.teacherForm.patchValue({
            teacherId: teacher.teacherId ?? teacher.teacherld ?? this.teacherId,
            fullName: teacher.fullName,
            contactNumber: teacher.contactNumber,
            email: teacher.email,
            subject: teacher.subject ?? teacher.specialty ?? '',
            yearsOfExperience: teacher.yearsOfExperience
          });
        },
        error: (error: any) => {
          console.error('Failed to load teacher details:', error);
          this.loadFallbackTeacherEdit();
        }
      });
    }

    if (service.getUserById && this.userId) {
      service.getUserById(this.userId).subscribe({
        next: (user: any) => {
          this.user = user;
          this.originalUsername = user.username || '';

          this.teacherForm.patchValue({
            username: user.username || ''
          });
        },
        error: (error: any) => {
          console.error('Failed to load user details:', error);

          this.teacherForm.patchValue({
            username: ''
          });
        }
      });
    }
  }

  loadFallbackTeacherEdit(): void {
    this.teacher = {
      teacherId: 0,
      fullName: 'Demo Teacher',
      contactNumber: '1234567890',
      email: 'demo.teacher@example.com',
      subject: 'Computer',
      yearsOfExperience: 1
    };

    this.originalUsername = 'demoTeacher';

    this.teacherForm.patchValue({
      teacherId: 0,
      username: 'demoTeacher',
      fullName: this.teacher.fullName,
      contactNumber: this.teacher.contactNumber,
      email: this.teacher.email,
      subject: this.teacher.subject,
      yearsOfExperience: this.teacher.yearsOfExperience
    });

    this.errorMessage = 'API failed. Showing fallback teacher details.';
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.teacherForm.invalid) {
      this.teacherForm.markAllAsTouched();
      this.errorMessage = 'Please correct the highlighted fields before updating.';
      return;
    }

    if (!this.teacherId || this.teacherId === 0) {
      this.errorMessage = 'Cannot update fallback teacher. Please login with a valid teacher.';
      return;
    }

    const formValue = this.teacherForm.value;

    const isUsernameChanged = formValue.username !== this.originalUsername;

    const payload: any = {
      teacherId: this.teacherId,
      username: formValue.username,

      /*
        Password is intentionally empty.
        Backend should not update password when password is empty.
        This prevents overwriting or double-encoding the existing BCrypt password.
      */
      password: '',

      fullName: formValue.fullName,
      contactNumber: formValue.contactNumber,
      email: formValue.email,
      subject: formValue.subject,
      yearsOfExperience: Number(formValue.yearsOfExperience)
    };

    console.log('Teacher update payload:', payload);

    const service: any = this.educonnectService;

    if (service.updateTeacher) {
      service.updateTeacher(payload).subscribe({
        next: () => {
          if (isUsernameChanged) {
            this.successMessage = 'Username updated successfully. Please login again.';
            this.errorMessage = null;

            setTimeout(() => {
              this.authService.logout();
              this.router.navigate(['/auth/login']);
            }, 1500);
          } else {
            this.successMessage = 'Teacher profile updated successfully!';
            this.errorMessage = null;

            setTimeout(() => {
              this.router.navigate(['/educonnect/dashboard']);
            }, 1000);
          }
        },
        error: (error: any) => {
          console.error('Failed to update teacher:', error);

          this.successMessage = null;
          this.errorMessage =
            error?.error?.message ||
            error?.error ||
            'Unable to update teacher profile.';
        }
      });
    }
  }

  resetForm(): void {
    this.loadTeacherDetails();
    this.successMessage = null;
    this.errorMessage = null;
  }

  goToDashboard(): void {
    this.router.navigate(['/educonnect/dashboard']);
  }
}