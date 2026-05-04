import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { EduconnectService } from '../../services/educonnect.service';
import { Student } from '../../models/Student';

@Component({
  selector: 'app-studentcreate',
  templateUrl: './studentcreate.component.html',
  styleUrls: ['./studentcreate.component.scss']
})
export class StudentCreateComponent implements OnInit {
  studentForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private educonnectService: EduconnectService
  ) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  initializeForm(): void {
    this.studentForm = this.fb.group({
      studentId: [0],
      fullName: ['', [Validators.required, Validators.minLength(2)]],
      dateOfBirth: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      address: ['', [Validators.required, Validators.minLength(5)]]
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.studentForm.invalid) {
      this.studentForm.markAllAsTouched();
      this.errorMessage = 'Please fill out all fields correctly.';
      return;
    }

    const formValue = this.studentForm.value;

    const student = new Student(
      formValue.studentId ?? 0,
      formValue.fullName,
      formValue.dateOfBirth,
      formValue.contactNumber,
      formValue.email,
      formValue.address
    );

    this.educonnectService.addStudent(student).subscribe({
      next: () => {
        this.successMessage = 'Student created successfully!';
        this.errorMessage = null;

        this.studentForm.reset({
          studentId: 0,
          fullName: '',
          dateOfBirth: '',
          contactNumber: '',
          email: '',
          address: ''
        });
      },
      error: (error: HttpErrorResponse) => this.handleError(error)
    });
  }

  private handleError(error: HttpErrorResponse): void {
    this.successMessage = null;
    this.errorMessage =
      error?.error?.message || 'Failed to create student. Please try again.';
  }
}