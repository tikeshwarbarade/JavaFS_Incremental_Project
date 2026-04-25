import { Component } from '@angular/core';

@Component({
  selector: 'app-studentsample',
  standalone: true,
  templateUrl: './studentsample.component.html',
  styleUrls: ['./studentsample.component.scss']
})
export class StudentSampleComponent {
  student: any;

  constructor() {
    this.student = {
      studentld: 1,
      fullName: 'John Doe',
      dateOfBirth: new Date('1990-01-01'),
      contactNumber: '1234567890',
      email: 'john@example.com',
      address: '123 Main Street'
    };
  }

  logStudentAttributes(): void {
    console.log('studentld:', this.student.studentld);
    console.log('fullName:', this.student.fullName);
    console.log('dateOfBirth:', this.student.dateOfBirth);
    console.log('contactNumber:', this.student.contactNumber);
    console.log('email:', this.student.email);
    console.log('address:', this.student.address);
  }
}