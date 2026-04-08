import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  loginForm: FormGroup;
  loading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit(): void {
    this.errorMessage = '';
    this.successMessage = '';

    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    const { username, password } = this.loginForm.value;

    this.authService.login(username, password).subscribe({
      next: (response) => {
        this.loading = false;
        if (response.success) {
          if (response.token) {
            this.authService.setToken(response.token);
          }
          this.successMessage = response.message || 'Login successful!';
          this.loginForm.reset();
          this.router.navigate(['/shop']);
        } else {
          this.errorMessage = response.message || 'Login failed.';
        }
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage =
          err.error?.message ||
          err.status === 0
            ? 'Cannot reach server. Is Spring Boot running on http://localhost:8080?'
            : 'Login failed. Please try again.';
      },
    });
  }
}
