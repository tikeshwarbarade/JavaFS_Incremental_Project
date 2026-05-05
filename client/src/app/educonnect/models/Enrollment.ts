import { Course } from "./Course";
import { Student } from "./Student";


export interface Enrollment {
  enrollmentId: number;
  course: Course;
  student: Student;
  enrollmentDate: Date;
}

