import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';

declare var bootstrap: any;

@Component({
  selector: 'app-register',
  imports:[ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})
export class Register {
  @ViewChild('messageModal') messageModal!:ElementRef;
  message: any;
  title:any;
  register = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required, Validators.minLength(8)])
  });

  constructor(private http: HttpClient, private router: Router) {}

  formSubmit() {
    const email = this.register.controls.email?.value;
    const username = this.register.controls.username?.value;
    const password = this.register.controls.password?.value;

    const tryRegister = { email, username, password };

    this.http.post('http://localhost:8080/api/register', tryRegister).subscribe({
      next: (response) => {
        console.log('Registration successful:', response);
        this.router.navigate(['/login']);
      },
      error: (err) => {
        console.error('Registration failed:', err);
        this.message = err.error.message;
        this.title="Registration failed";
        const modal = new bootstrap.Modal(this.messageModal.nativeElement);
        modal.show();
      }
    });
  }
}