import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile-page',
  imports: [ReactiveFormsModule],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css'
})
export class ProfilePage implements OnInit {
  data: any;
  update!: FormGroup;
  message: any;
  constructor(private http: HttpClient, private router: Router){}
  ngOnInit(){
    this.http.get<any>(`http://localhost:8080/api/profile`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('accessToken')}`
      }}).subscribe({
      next: (resp) =>{
        this.data = resp;
        console.log(this.data);
        this.update = new FormGroup({
          email: new FormControl(this.data.email, [Validators.required, Validators.email]),
          username: new FormControl(this.data.username, [Validators.required]),
          password: new FormControl('', [Validators.required, Validators.minLength(8)])
        });
      },
      error: (err)=>{
        console.log("error",err);
      }
    });
  }
  formSubmit(){
    this.message = "";
    const email = this.update.controls['email'].value;
    const username = this.update.controls['username'].value;
    const password = this.update.controls['password'].value;
    let tryUpdate = {email,username, password};
    this.http.put(`http://localhost:8080/api/updateProfile`,tryUpdate,{
      headers:{
        Authorization: `Bearer ${localStorage.getItem('accessToken')}`
      }
    }).subscribe({
      next: (resp) =>{
        console.log(resp);
        this.message = resp;
        this.router.navigate(["/home"]).then(
          ()=>
          {
            window.location.reload()
          }
        )
      },
      error:(err)=>{
        console.error(err);
        this.message = err.error.message;
      }
    })
  }
}
