import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { EduConnectService } from '../../services/educonnect.service';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-coursecreate',
  templateUrl: './coursecreate.component.html',
  styleUrls: ['./coursecreate.component.scss']
})
export class CourseCreateComponent implements OnInit {
  courseForm!: FormGroup;

  successMessage: string | null = null;
  errorMessage: string | null = null;

  teacherId: number = 0;
  teacherName: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private eduConnectService: EduConnectService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.teacherId = this.authService.getTeacherId();

    this.courseForm = this.formBuilder.group({
      courseId: [0],
      courseName: ['', Validators.required],
      description: ['', Validators.required],
      teacherId: [this.teacherId || 0, Validators.required],
      teacherName: [{ value: '', disabled: true }]
    });

    this.loadTeacherDetails();
  }

  loadTeacherDetails(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (!this.teacherId || this.teacherId === 0) {
      this.errorMessage = 'Teacher ID not found. Please login again.';
      return;
    }

    this.eduConnectService.getTeacherById(this.teacherId).subscribe({
      next: (teacher: any) => {
        this.teacherName = teacher.fullName || '';

        this.courseForm.patchValue({
          teacherId: teacher.teacherId,
          teacherName: teacher.fullName
        });

        this.courseForm.markAsPristine();
        this.courseForm.markAsUntouched();
      },
      error: (error: any) => {
        console.error('Failed to load teacher details:', error);
        this.errorMessage = 'Unable to load teacher details.';
      }
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.courseForm.invalid) {
      this.courseForm.markAllAsTouched();
      this.errorMessage = 'Please fill out all fields correctly.';
      return;
    }

    const formValue = this.courseForm.getRawValue();

    const coursePayload = {
      courseId: 0,
      courseName: formValue.courseName,
      description: formValue.description,
      teacher: {
        teacherId: Number(formValue.teacherId)
      }
    };

    console.log('Course Create Payload:', coursePayload);

    this.eduConnectService.addCourse(coursePayload).subscribe({
      next: () => {
        this.successMessage = 'Course created successfully!';
        this.errorMessage = null;

        this.resetFormAfterSuccess();
      },
      error: (error: any) => {
        console.error('Failed to create course:', error);

        this.successMessage = null;
        this.errorMessage =
          error?.error?.message ||
          error?.error ||
          'Failed to create course.';
      }
    });
  }

  resetForm(): void {
    this.courseForm.reset({
      courseId: 0,
      courseName: '',
      description: '',
      teacherId: this.teacherId || 0,
      teacherName: {
        value: this.teacherName || '',
        disabled: true
      }
    });

    this.courseForm.markAsPristine();
    this.courseForm.markAsUntouched();
    this.courseForm.updateValueAndValidity();

    this.successMessage = null;
    this.errorMessage = null;
  }

  resetFormAfterSuccess(): void {
    this.courseForm.reset({
      courseId: 0,
      courseName: '',
      description: '',
      teacherId: this.teacherId || 0,
      teacherName: {
        value: this.teacherName || '',
        disabled: true
      }
    });

    this.courseForm.markAsPristine();
    this.courseForm.markAsUntouched();
    this.courseForm.updateValueAndValidity();
  }

  goToDashboard(): void {
    this.router.navigate(['/educonnect/dashboard']);
  }
}