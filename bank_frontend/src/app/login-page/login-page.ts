import { HttpClient } from '@angular/common/http';
import { Component, resolveForwardRef } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  standalone:false,
  templateUrl: './login-page.html',
  styleUrl: './login-page.css'
})
export class LoginPage {
  login = new FormGroup(
    {
      username: new FormControl('',[Validators.required]),
      password: new FormControl('',[Validators.required])
    });
  constructor(private http: HttpClient, private router: Router) {}
  formSubmit(){
    let username = this.login.controls.username.value;
    let pass = this.login.controls.password.value;
    let tryUser = {username,pass}
    let resp = this.http.post('http://localhost:8080/api/login',tryUser);
    console.log(resp.subscribe({next: (response =>{console.log(response)})}));
    return resp;
  }
  /*formSubmit(){
    let username = this.login.controls.username.value;
    let pass = this.login.controls.password.value;
    let tryUser = {username,pass}
    console.log(tryUser);
    this.http.post<{accessToken: string, user:any}>('localhost:8080/api/login',tryUser).subscribe({
      next: (response) =>{
        localStorage.setItem('accessToken',response.accessToken)
      }
    })

  }*/
}
