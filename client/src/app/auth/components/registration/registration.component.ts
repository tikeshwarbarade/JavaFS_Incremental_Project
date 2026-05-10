import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

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

  showSuccessBox: boolean = false;
  showErrorBox: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(/^(?=.*[A-Z])(?=.*[0-9]).{8,}$/)
        ]
      ],
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
    }

    if (role === 'STUDENT') {
      this.registrationForm.get('dateOfBirth')?.setValidators([Validators.required]);
      this.registrationForm.get('address')?.setValidators([Validators.required]);
    }

    this.registrationForm.get('subject')?.updateValueAndValidity();
    this.registrationForm.get('yearsOfExperience')?.updateValueAndValidity();
    this.registrationForm.get('dateOfBirth')?.updateValueAndValidity();
    this.registrationForm.get('address')?.updateValueAndValidity();
  }

  onSubmit(): void {
    this.clearMessages();

    if (this.registrationForm.invalid) {
      this.registrationForm.markAllAsTouched();
      this.showError('Please fill out all fields correctly.');
      return;
    }

    const formValue = this.registrationForm.value;

    let userData: any = {
      username: formValue.username,
      password: formValue.password,
      role: formValue.role,
      fullName: formValue.fullName,
      contactNumber: formValue.contactNumber,
      email: formValue.email
    };

    if (formValue.role === 'STUDENT') {
      userData = {
        ...userData,
        dateOfBirth: formValue.dateOfBirth,
        address: formValue.address
      };
    }

    if (formValue.role === 'TEACHER') {
      userData = {
        ...userData,
        subject: formValue.subject,
        yearsOfExperience: Number(formValue.yearsOfExperience)
      };
    }

    console.log('Registration Payload:', userData);

    this.authService.createUser(userData).subscribe({
      next: () => {
        const roleText =
          formValue.role === 'STUDENT'
            ? 'Student'
            : formValue.role === 'TEACHER'
              ? 'Teacher'
              : 'User';

        this.showSuccess(`${roleText} registered successfully!`);
        this.resetForm();
      },
      error: (error) => {
        console.error('Registration failed:', error);
        this.showError(this.extractErrorMessage(error));
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

  private clearMessages(): void {
    this.successMessage = null;
    this.errorMessage = null;
    this.showSuccessBox = false;
    this.showErrorBox = false;
  }

  private showSuccess(message: string): void {
    this.successMessage = message;
    this.errorMessage = null;
    this.showSuccessBox = true;
    this.showErrorBox = false;
  }

  private showError(message: string): void {
    this.errorMessage = message;
    this.successMessage = null;
    this.showErrorBox = true;
    this.showSuccessBox = false;
  }

  private extractErrorMessage(error: any): string {
    if (!error) {
      return 'Registration failed. Please try again.';
    }

    if (typeof error.error === 'string') {
      return error.error;
    }

    if (error.error?.message) {
      return error.error.message;
    }

    if (error.message) {
      return error.message;
    }

    return 'Registration failed. Please try again.';
  }
}