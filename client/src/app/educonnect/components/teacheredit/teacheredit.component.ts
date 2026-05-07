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

  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private educonnectService: EduconnectService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.teacherForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
      fullName: ['', Validators.required],
      contactNumber: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      subject: ['', Validators.required],
      yearsOfExperience: [0, Validators.required]
    });

    const auth: any = this.authService;
    this.teacherId = auth.getTeacherId ? auth.getTeacherId() : this.teacherId;
    this.userId = auth.getUserId ? auth.getUserId() : this.userId;

    this.loadTeacherDetails();
  }

  loadTeacherDetails(): void {
    const service: any = this.educonnectService;

    if (service.getTeacherById) {
      service.getTeacherById(this.teacherId).subscribe({
        next: (teacher: any) => {
          this.teacher = teacher;

          this.teacherForm.patchValue({
            fullName: teacher.fullName,
            contactNumber: teacher.contactNumber,
            email: teacher.email,
            subject: teacher.subject ?? teacher.specialty ?? '',
            yearsOfExperience: teacher.yearsOfExperience
          });
        },
        error: () => {
          this.teacher = undefined;
        }
      });
    }

    if (service.getUserById) {
      service.getUserById(this.userId).subscribe({
        next: (user: any) => {
          this.user = user;

          this.teacherForm.patchValue({
            username: user.username,
            password: user.password
          });
        },
        error: () => {
          this.user = undefined;
        }
      });
    }
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    const service: any = this.educonnectService;
    const payload = this.teacherForm.value;

    if (service.updateTeacher) {
      service.updateTeacher(payload).subscribe({
        next: () => {
          this.successMessage = 'Teacher updated successfully!';
          this.errorMessage = null;
        },
        error: () => {
          this.successMessage = null;
          this.errorMessage = 'Unable to update teacher.';
        }
      });
    }
  }

  resetForm(): void {
    this.teacherForm.reset();
    this.successMessage = null;
    this.errorMessage = null;
  }

  goBack(): void {
    this.router.navigate(['/dashboard']);
  }
}