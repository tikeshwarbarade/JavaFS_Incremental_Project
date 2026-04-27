import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { StudentCreateComponent } from './components/studentcreate/studentcreate.component';
import { TeacherArrayComponent } from './components/teacherarray/teacherarray.component';

@NgModule({
  declarations: [
    StudentCreateComponent,
    TeacherArrayComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    StudentCreateComponent,
    TeacherArrayComponent
  ]
})
export class EduconnectModule { }