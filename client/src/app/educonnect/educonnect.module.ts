import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule } from '@angular/router';

import { SharedModule } from '../shared/shared.module';
import { EduConnectRoutingModule } from './educonnect-routing.module';

import { StudentCreateComponent } from './components/studentcreate/studentcreate.component';
import { TeacherCreateComponent } from './components/teachercreate/teachercreate.component';
import { CourseCreateComponent } from './components/coursecreate/coursecreate.component';
import { EnrollmentComponent } from './components/enrollment/enrollment.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

@NgModule({
  declarations: [
    StudentCreateComponent,
    TeacherCreateComponent,
    CourseCreateComponent,
    EnrollmentComponent,
    DashboardComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    SharedModule,
    EduConnectRoutingModule
  ]
})
export class EduconnectModule { }