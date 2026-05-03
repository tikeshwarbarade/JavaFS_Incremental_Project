import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

interface Course {
  courseId: number;
  courseName: string;
  description: string;
  teacherId: number;
}

@Component({
  selector: 'app-coursecreate',
  templateUrl: './coursecreate.component.html',
  styleUrls: ['./coursecreate.component.scss']
})
export class CourseCreateComponent implements OnInit {
  courseForm!: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.courseForm = this.fb.group({
      courseId: [0],
      courseName: ['', Validators.required],
      description: ['', Validators.maxLength(500)],
      teacherId: [null, [Validators.required, Validators.min(1)]]
    });
  }

  onSubmit(): void {
    this.successMessage = '';
    this.errorMessage = '';

    if (this.courseForm.valid) {
      const course: Course = {
        courseId: this.courseForm.value.courseId,
        courseName: this.courseForm.value.courseName,
        description: this.courseForm.value.description,
        teacherId: this.courseForm.value.teacherId
      };

      console.log('Course Created:', course);
      this.successMessage = 'Course created successfully!';
      this.errorMessage = '';

      this.courseForm.reset({
        courseId: 0,
        courseName: '',
        description: '',
        teacherId: null
      });
    } else {
      this.courseForm.markAllAsTouched();
      this.errorMessage = 'Please correct the errors in the form before submitting.';
      this.successMessage = '';
    }
  }

  resetForm(): void {
    this.courseForm.reset({
      courseId: 0,
      courseName: '',
      description: '',
      teacherId: null
    });
    this.successMessage = '';
    this.errorMessage = '';
  }
}
