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
  teacherId: number = 1;

  successMessage: string | null = null;
  errorMessage: string | null = null;
  course: any;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private educonnectService: EduconnectService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.courseForm = this.fb.group({
      courseId: [0],
      courseName: ['', Validators.required],
      description: ['', Validators.required],
      teacherId: [0, Validators.required]
    });

    const auth: any = this.authService;
    this.teacherId = auth.getTeacherId ? auth.getTeacherId() : this.teacherId;

    const routeId = this.route.snapshot.paramMap.get('id');
    this.courseId = routeId ? +routeId : 0;

    this.loadCourseDetails();
  }

  loadCourseDetails(): void {
    const service: any = this.educonnectService;

    if (service.getCourseById && this.courseId) {
      service.getCourseById(this.courseId).subscribe((course: any) => {
        this.course = course;
        this.courseForm.patchValue({
          courseId: course.courseId,
          courseName: course.courseName,
          description: course.description,
          teacherId: course.teacherId ?? this.teacherId
        });
      });
    }
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.courseForm.invalid) {
      this.errorMessage = 'Please fill in all required fields.';
      return;
    }

    const service: any = this.educonnectService;
    if (service.updateCourse) {
      service.updateCourse(this.courseForm.value).subscribe(() => {
        this.successMessage = 'Course updated successfully!';
      });
    }
  }

  resetForm(): void {
    this.courseForm.reset();
    this.successMessage = null;
    this.errorMessage = null;
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }
}