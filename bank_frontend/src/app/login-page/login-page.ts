import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AppRoutingModule } from '../app.routes';

@Component({
  selector: 'app-login-page',
  imports:[ReactiveFormsModule],
  templateUrl: './login-page.html',
  styleUrl: './login-page.css'
})
export class LoginPage {
  login = new FormGroup(
    {
      email: new FormControl('',[Validators.required,Validators.email]),
      password: new FormControl('',[Validators.required])
    });
  constructor(private http: HttpClient, private router: Router) {}
  formSubmit(){
    let email = this.login.controls.email.value;
    let password = this.login.controls.password.value;
    let tryUser = { email, password }
    this.http.post<{accessToken: string}>('http://localhost:8080/api/login',tryUser).subscribe(
      {
        next: (response) =>{
          localStorage.setItem('accessToken',response.accessToken);
          this.router.navigate(['/home']);
        },
        error:(err)=>{
          console.error("error: ",err);
        }
      }
      
      
    );
  }
}
