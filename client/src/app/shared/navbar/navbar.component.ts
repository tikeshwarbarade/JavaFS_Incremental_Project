import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavBarComponent {
  role: string = '';
  studentName: string = '';

  constructor() {
    this.loadUser();
  }

  loadUser(): void {
    const rawUser =
      localStorage.getItem('currentUser') ||
      localStorage.getItem('loginResponse');

    if (rawUser) {
      const user = JSON.parse(rawUser);
      this.role = user.role || user.roles || '';

      if (this.role === 'STUDENT') {
        const studentRaw = localStorage.getItem('currentStudent');
        if (studentRaw) {
          const student = JSON.parse(studentRaw);
          this.studentName = student.fullName || '';
        }
      }
    }
  }

  goToEnrollment(): void {
    localStorage.setItem('dashboardSection', 'enrollment');
  }

  goToDashboard(): void {
    localStorage.setItem('dashboardSection', 'dashboard');
  }

  logout(): void {
    localStorage.removeItem('dashboardSection');
  }
}
