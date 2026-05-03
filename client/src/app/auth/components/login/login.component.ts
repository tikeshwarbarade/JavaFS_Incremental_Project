import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  errorMessage: string | null = null;
  successMessage: string | null = null;

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[A-Z])(?=.*[0-9]).{8,}$/)]]
    });
  }
onSubmit(): void {
  this.successMessage = null;
  this.errorMessage = null;

  if (this.loginForm.valid) {
    this.successMessage = 'Login successful!';
    this.errorMessage = null;
  } else {
    this.loginForm.markAllAsTouched();
    this.errorMessage = 'Please fill out all fields correctly.';
    this.successMessage = null;
  }
}
}