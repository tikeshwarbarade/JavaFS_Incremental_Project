import { Student } from './Student';
import { Teacher } from './Teacher';

export class User {
  userId: number;
  username: string;
  password: string;
  role: string;
  student?: Student;
  teacher?: Teacher;

  constructor(
    userId: number,
    username: string,
    password: string,
    role: string,
    student?: Student,
    teacher?: Teacher
  ) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.role = role;
    this.student = student;
    this.teacher = teacher;
  }
}