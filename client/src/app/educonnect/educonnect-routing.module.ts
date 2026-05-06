import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { StudentEditComponent } from './components/studentedit/studentedit.component';
import { TeacherEditComponent } from './components/teacheredit/teacheredit.component';
import { CourseEditComponent } from './components/courseedit/courseedit.component';
import { EnrollmentComponent } from './components/enrollment/enrollment.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'student-edit', component: StudentEditComponent },
  { path: 'teacher-edit', component: TeacherEditComponent },
  { path: 'course-edit/:id', component: CourseEditComponent },
  { path: 'enrollment', component: EnrollmentComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EduconnectRoutingModule {}