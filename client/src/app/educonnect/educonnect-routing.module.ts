import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { StudentCreateComponent } from './components/studentcreate/studentcreate.component';
import { TeacherCreateComponent } from './components/teachercreate/teachercreate.component';
import { CourseCreateComponent } from './components/coursecreate/coursecreate.component';
import { EnrollmentComponent } from './components/enrollment/enrollment.component';

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'student', component: StudentCreateComponent },
  { path: 'teacher', component: TeacherCreateComponent },
  { path: 'course', component: CourseCreateComponent },
  { path: 'course/edit/:id', component: CourseCreateComponent },
  { path: 'enrollment', component: EnrollmentComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EduConnectRoutingModule {}