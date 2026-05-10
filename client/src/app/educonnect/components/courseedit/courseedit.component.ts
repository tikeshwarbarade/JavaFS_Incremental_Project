import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { EduconnectService } from '../../services/educonnect.service';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-courseedit',
  templateUrl: './courseedit.component.html',
  styleUrls: ['./courseedit.component.scss']
})
export class CourseEditComponent implements OnInit {
  courseForm!: FormGroup;

  courseId: number = 0;
  teacherId: number = 0;
  teacherName: string = '';

  course: any;

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private educonnectService: EduconnectService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const auth: any = this.authService;

    this.teacherId = auth.getTeacherId ? auth.getTeacherId() : 0;

    this.courseForm = this.fb.group({
      courseId: [0],
      courseName: ['', Validators.required],
      description: ['', Validators.required],
      teacherId: [this.teacherId || 0, Validators.required],
      teacherName: [{ value: '', disabled: true }]
    });

    const routeId = this.route.snapshot.paramMap.get('id');
    this.courseId = routeId ? Number(routeId) : 0;

    this.loadTeacherDetails();
    this.loadCourseDetails();
  }

  loadTeacherDetails(): void {
    if (!this.teacherId) {
      return;
    }

    this.educonnectService.getTeacherById(this.teacherId).subscribe({
      next: (teacher: any) => {
        this.teacherName = teacher.fullName || '';

        this.courseForm.patchValue({
          teacherName: this.teacherName,
          teacherId: teacher.teacherId
        });
      },
      error: (error: any) => {
        console.error('Failed to load teacher details:', error);
      }
    });
  }

  loadCourseDetails(): void {
    if (!this.courseId) {
      this.errorMessage = 'Course ID not found.';
      return;
    }

    this.educonnectService.getCourseById(this.courseId).subscribe({
      next: (course: any) => {
        this.course = course;

        const courseTeacherId =
          course.teacher?.teacherId ||
          course.teacherId ||
          this.teacherId;

        const courseTeacherName =
          course.teacher?.fullName ||
          this.teacherName ||
          '';

        this.courseForm.patchValue({
          courseId: course.courseId,
          courseName: course.courseName,
          description: course.description,
          teacherId: courseTeacherId,
          teacherName: courseTeacherName
        });
      },
      error: (error: any) => {
        console.error('Failed to load course:', error);
        this.errorMessage = 'Unable to load course details.';
      }
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.courseForm.invalid) {
      this.courseForm.markAllAsTouched();
      this.errorMessage = 'Please fill in all required fields.';
      return;
    }

    const formValue = this.courseForm.getRawValue();

    const coursePayload: any = {
      courseId: this.courseId,
      courseName: formValue.courseName,
      description: formValue.description,
      teacher: {
        teacherId: Number(formValue.teacherId)
      }
    };

    this.educonnectService.updateCourse(coursePayload).subscribe({
      next: () => {
        this.successMessage = 'Course updated successfully!';
        this.errorMessage = null;

        setTimeout(() => {
          this.router.navigate(['/educonnect/dashboard']);
        }, 1000);
      },
      error: (error: any) => {
        console.error('Failed to update course:', error);

        this.successMessage = null;
        this.errorMessage =
          error?.error?.message ||
          error?.error ||
          'Unable to update course.';
      }
    });
  }

  resetForm(): void {
    this.loadCourseDetails();
    this.successMessage = null;
    this.errorMessage = null;
  }

  goBack(): void {
    this.router.navigate(['/educonnect/dashboard']);
  }
}