import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { EduconnectService } from '../../services/educonnect.service';
import { Teacher } from '../../models/Teacher';

@Component({
  selector: 'app-teachercreate',
  templateUrl: './teachercreate.component.html',
  styleUrls: ['./teachercreate.component.scss']
})
export class TeacherCreateComponent implements OnInit {
  teacherForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private educonnectService: EduconnectService
  ) {}

  ngOnInit(): void {
    this.teacherForm = this.fb.group({
      teacherId: [0],
      fullName: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      subject: ['', Validators.required],
      yearsOfExperience: [0, [Validators.required, Validators.min(1)]]
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.teacherForm.invalid) {
      this.teacherForm.markAllAsTouched();
      this.errorMessage = 'Please fill out all fields correctly.';
      return;
    }

    const formValue = this.teacherForm.value;

    const teacher = new Teacher(
      formValue.teacherId ?? 0,
      formValue.fullName,
      formValue.contactNumber,
      formValue.email,
      formValue.subject,
      formValue.yearsOfExperience
    );

    this.educonnectService.addTeacher(teacher).subscribe({
      next: () => {
        this.successMessage = 'Teacher created successfully!';
        this.errorMessage = null;

        this.teacherForm.reset({
          teacherId: 0,
          fullName: '',
          contactNumber: '',
          email: '',
          subject: '',
          yearsOfExperience: 0
        });
      },
      error: (error: HttpErrorResponse) => this.handleError(error)
    });
  }

  private handleError(error: HttpErrorResponse): void {
    this.successMessage = null;
    this.errorMessage =
      error?.error?.message || 'Failed to create teacher. Please try again.';
  }
}