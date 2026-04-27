import { Component } from '@angular/core';
import { Teacher } from '../../models/Teacher';

@Component({
  selector: 'app-teacherarray',
  templateUrl: './teacherarray.component.html',
  styleUrls: ['./teacherarray.component.scss']
})
export class TeacherArrayComponent {
  teachers: Teacher[];
  showDetails: boolean;

  constructor() {
    this.teachers = [
      new Teacher(
        1,
        'Dr. Jane Smith',
        '9876543210',
        'jane@example.com',
        'English',
        15
      ),
      new Teacher(
        2,
        'Mr. Ravi Kumar',
        '9123456780',
        'ravi@example.com',
        'Mathematics',
        10
      ),
      new Teacher(
        3,
        'Ms. Anita Rao',
        '9988776655',
        'anita@example.com',
        'Science',
        8
      )
    ];

    this.showDetails = true;
  }

  toggleDetails(): void {
    this.showDetails = !this.showDetails;
  }
}
