import { Component } from '@angular/core';
import { Student } from '../../models/Student';

@Component({
  selector: 'app-studentcreate',
  templateUrl: './studentcreate.component.html',
  styleUrls: ['./studentcreate.component.scss']
})
export class StudentCreateComponent {
  student: Student;
  successMessage: string | null;
  errorMessage: string | null;

  constructor() {
    this.student = new Student(
      0,
      '',
      null,
      '',
      '',
      ''
    );

    this.successMessage = null;
    this.errorMessage = null;
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (
      !this.student.fullName ||
      !this.student.dateOfBirth ||
      !this.student.contactNumber ||
      !this.student.email ||
      !this.student.address
    ) {
      this.errorMessage = "Please fill in all required fields.";
      return;
    }

    this.successMessage = "Student created successfully!";
  }

  resetForm(): void {
    this.student = new Student(
      0,
      '',
      null,
      '',
      '',
      ''
    );

    this.successMessage = null;
    this.errorMessage = null;
  }
}