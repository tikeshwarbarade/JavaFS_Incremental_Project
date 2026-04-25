import { Component } from '@angular/core';
import { Teacher } from '../../models/Teacher';

@Component({
    standalone:true,
  selector: 'app-teachersample',
  templateUrl: './teachersample.component.html',
  styleUrls: ['./teachersample.component.scss']
})
export class TeacherSampleComponent {
  teacher: Teacher;

  constructor() {
    this.teacher = new Teacher(
      1,
      'Dr. Jane Smith',
      '9876543210',
      'jane@example.com',
      'English',
      15
    );
  }

  logTeacherAttributes(): void {
    this.teacher.logAttributes();
  }
}