import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

interface Enrollment {
  enrollmentId: number | null;
  studentId: number | null;
  courseId: number | null;
  enrollmentDate: Date | null;
}

@Component({
  selector: 'app-enrollment',
  templateUrl: './enrollment.component.html',
  styleUrls: ['./enrollment.component.scss']
})
export class EnrollmentComponent implements OnInit {
  enrollmentForm!: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.enrollmentForm = this.fb.group({
      enrollmentId: [null],
      studentId: [null, Validators.required],
      courseId: [null, Validators.required],
      enrollmentDate: [null, Validators.required]
    });
  }

  onSubmit(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.enrollmentForm.valid) {
      const enrollment: Enrollment = {
        enrollmentId: this.enrollmentForm.value.enrollmentId,
        studentId: this.enrollmentForm.value.studentId,
        courseId: this.enrollmentForm.value.courseId,
        enrollmentDate: this.enrollmentForm.value.enrollmentDate
      };

      console.log('Enrollment Added:', enrollment);
      this.successMessage = 'Enrollment added successfully!';
      this.errorMessage = '';

      this.enrollmentForm.reset({
        enrollmentId: null,
        studentId: null,
        courseId: null,
        enrollmentDate: null
      });
    } else {
      this.enrollmentForm.markAllAsTouched();
      this.errorMessage = 'Please fill in all required fields correctly.';
      this.successMessage = '';
    }
  }

  resetForm(): void {
    this.enrollmentForm.reset({
      enrollmentId: null,
      studentId: null,
      courseId: null,
      enrollmentDate: null
    });
    this.successMessage = '';
    this.errorMessage = '';
  }
}
