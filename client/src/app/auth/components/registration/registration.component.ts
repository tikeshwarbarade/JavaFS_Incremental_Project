import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { UserRegistrationDTO } from '../../../educonnect/models/UserRegistrationDTO';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  registrationForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  selectedRole: string | null = null;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
      password: ['', [Validators.required, Validators.minLength(8), Validators.pattern(/^(?=.*[A-Z])(?=.*[0-9]).{8,}$/)]],
      role: ['', Validators.required],
      fullName: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      subject: [''],
      yearsOfExperience: [''],
      dateOfBirth: [''],
      address: ['']
    });
  }

  onRoleChange(event: Event): void {
    const role = (event.target as HTMLSelectElement).value;
    this.selectedRole = role;

    this.registrationForm.get('subject')?.clearValidators();
    this.registrationForm.get('yearsOfExperience')?.clearValidators();
    this.registrationForm.get('dateOfBirth')?.clearValidators();
    this.registrationForm.get('address')?.clearValidators();

    this.registrationForm.get('subject')?.setValue('');
    this.registrationForm.get('yearsOfExperience')?.setValue('');
    this.registrationForm.get('dateOfBirth')?.setValue('');
    this.registrationForm.get('address')?.setValue('');

    if (role === 'TEACHER') {
      this.registrationForm.get('subject')?.setValidators([Validators.required]);
      this.registrationForm.get('yearsOfExperience')?.setValidators([
        Validators.required,
        Validators.min(1)
      ]);
      this.registrationForm.get('address')?.setValidators([Validators.required]);
    } else if (role === 'STUDENT') {
      this.registrationForm.get('dateOfBirth')?.setValidators([Validators.required]);
      this.registrationForm.get('address')?.setValidators([Validators.required]);
    }

    this.registrationForm.get('subject')?.updateValueAndValidity();
    this.registrationForm.get('yearsOfExperience')?.updateValueAndValidity();
    this.registrationForm.get('dateOfBirth')?.updateValueAndValidity();
    this.registrationForm.get('address')?.updateValueAndValidity();
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.registrationForm.invalid) {
      this.registrationForm.markAllAsTouched();
      this.errorMessage = 'Please fill out all fields correctly.';
      return;
    }

    const userData: UserRegistrationDTO = this.registrationForm.value;

    this.authService.createUser(userData).subscribe({
      next: () => {
        this.successMessage = 'Registration successful!';
        this.errorMessage = null;
        this.resetForm();
      },
      error: () => {
        this.errorMessage = 'Registration failed. Please try again.';
        this.successMessage = null;
      }
    });
  }

  resetForm(): void {
    this.registrationForm.reset({
      username: '',
      password: '',
      role: '',
      fullName: '',
      contactNumber: '',
      email: '',
      subject: '',
      yearsOfExperience: '',
      dateOfBirth: '',
      address: ''
    });

    this.registrationForm.get('subject')?.clearValidators();
    this.registrationForm.get('yearsOfExperience')?.clearValidators();
    this.registrationForm.get('dateOfBirth')?.clearValidators();
    this.registrationForm.get('address')?.clearValidators();

    this.registrationForm.get('subject')?.updateValueAndValidity();
    this.registrationForm.get('yearsOfExperience')?.updateValueAndValidity();
    this.registrationForm.get('dateOfBirth')?.updateValueAndValidity();
    this.registrationForm.get('address')?.updateValueAndValidity();

    this.selectedRole = null;
  }
}