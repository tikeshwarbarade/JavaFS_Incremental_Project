import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EduconnectRoutingModule } from './educonnect-routing.module';
import { SharedModule } from '../shared/shared.module';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { StudentEditComponent } from './components/studentedit/studentedit.component';
import { TeacherEditComponent } from './components/teacheredit/teacheredit.component';
import { CourseCreateComponent } from './components/coursecreate/coursecreate.component';
import { CourseEditComponent } from './components/courseedit/courseedit.component';
import { EnrollmentComponent } from './components/enrollment/enrollment.component';

@NgModule({
  declarations: [
    DashboardComponent,
    StudentEditComponent,
    TeacherEditComponent,
    CourseCreateComponent,
    CourseEditComponent,
    EnrollmentComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    EduconnectRoutingModule,
    SharedModule
  ]
})
export class EduconnectModule {}