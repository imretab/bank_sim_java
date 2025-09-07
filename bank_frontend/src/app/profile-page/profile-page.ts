import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProfileService } from '../services/profile-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-profile-page',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './profile-page.html',
  styleUrl: './profile-page.css'
})
export class ProfilePage implements OnInit {
  data: any;
  update!: FormGroup;
  message: any;
  title: any;
  updateFormReset(){
    this.update = new FormGroup({
      email: new FormControl(this.data.email, [Validators.required, Validators.email]),
      username: new FormControl(this.data.username, [Validators.required]),
      password: new FormControl('', [Validators.required, Validators.minLength(8)])
    });
  }
  constructor(private profileService: ProfileService, private router: Router){}
  ngOnInit(){
    this.profileService.getProfileValues().subscribe({
      next: (resp) =>{
        this.data = resp;
        this.updateFormReset();
      },
      error: (err)=>{
        console.log("error",err);
      }
    });
  }
  formSubmit(){
    const email = this.update.controls['email'].value;
    const username = this.update.controls['username'].value;
    const password = this.update.controls['password'].value;
    let tryUpdate = {email,username, password};
    this.profileService.putProfileValues(tryUpdate).subscribe({
      next: (resp) =>{
        console.log(resp);
        this.message = resp.message;
        this.title="Success Message";
        this.updateFormReset();
      },
      error:(err)=>{
        console.error(err);
        this.message = err.error.message;
        this.title ="Error Message";
      }
    })
  }
}
