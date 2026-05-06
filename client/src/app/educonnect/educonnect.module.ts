import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EduconnectRoutingModule } from './educonnect-routing.module';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TeacherEditComponent } from './components/teacheredit/teacheredit.component';
import { CourseEditComponent } from './components/courseedit/courseedit.component';
import { StudentEditComponent } from './components/studentedit/studentedit.component';
import { EnrollmentComponent } from './components/enrollment/enrollment.component';

@NgModule({
  declarations: [
    DashboardComponent,
    TeacherEditComponent,
    CourseEditComponent,
    StudentEditComponent,
    EnrollmentComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    EduconnectRoutingModule
  ],
  exports: [
    DashboardComponent,
    TeacherEditComponent,
    CourseEditComponent,
    StudentEditComponent,
    EnrollmentComponent
  ]
})
export class EduconnectModule {}