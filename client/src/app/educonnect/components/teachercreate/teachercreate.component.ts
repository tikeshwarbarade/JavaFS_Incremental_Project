import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

interface Teacher {
  teacherId: number;
  fullName: string;
  contactNumber: string;
  email: string;
  subject: string;
  yearsOfExperience: number;
}

@Component({
  selector: 'app-teachercreate',
  templateUrl: './teachercreate.component.html',
  styleUrls: ['./teachercreate.component.scss']
})
export class TeacherCreateComponent implements OnInit {
  teacherForm!: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private fb: FormBuilder) {}

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
    this.successMessage = '';
    this.errorMessage = '';

    if (this.teacherForm.valid) {
      const teacher: Teacher = {
        teacherId: this.teacherForm.value.teacherId,
        fullName: this.teacherForm.value.fullName,
        contactNumber: this.teacherForm.value.contactNumber,
        email: this.teacherForm.value.email,
        subject: this.teacherForm.value.subject,
        yearsOfExperience: this.teacherForm.value.yearsOfExperience
      };

      console.log('Teacher Created:', teacher);
      this.successMessage = 'Teacher created successfully!';
      this.errorMessage = '';

      this.teacherForm.reset({
        teacherId: 0,
        fullName: '',
        contactNumber: '',
        email: '',
        subject: '',
        yearsOfExperience: 0
      });
    } else {
      this.teacherForm.markAllAsTouched();
      this.errorMessage = 'Please correct the errors in the form before submitting.';
      this.successMessage = '';
    }
  }

  resetForm(): void {
    this.teacherForm.reset({
      teacherId: 0,
      fullName: '',
      contactNumber: '',
      email: '',
      subject: '',
      yearsOfExperience: 0
    });
    this.successMessage = '';
    this.errorMessage = '';
  }
}