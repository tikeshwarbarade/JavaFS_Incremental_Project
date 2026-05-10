import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavBarComponent implements OnInit {
  role: string = '';
  studentName: string = '';
  teacherName: string = '';

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadUser();
  }

  loadUser(): void {
    this.role = this.authService.getRole ? this.authService.getRole() : '';

    const currentStudent = localStorage.getItem('currentStudent');
    const currentTeacher = localStorage.getItem('currentTeacher');

    if (currentStudent) {
      try {
        const student = JSON.parse(currentStudent);
        this.studentName = student.fullName || '';
      } catch {
        this.studentName = '';
      }
    }

    if (currentTeacher) {
      try {
        const teacher = JSON.parse(currentTeacher);
        this.teacherName = teacher.fullName || '';
      } catch {
        this.teacherName = '';
      }
    }
  }

  goToDashboard(): void {
    this.router.navigate(['/educonnect/dashboard']);
  }

  goToEnrollment(): void {
    this.router.navigate(['/educonnect/enrollment']);
  }

  goToCourseCreate(): void {
    this.router.navigate(['/educonnect/course']);
  }

  logout(): void {
    if (this.authService.logout) {
      this.authService.logout();
    }

    localStorage.removeItem('dashboardSection');
    localStorage.removeItem('currentStudent');
    localStorage.removeItem('currentTeacher');

    this.router.navigate(['/auth/login']);
  }
}