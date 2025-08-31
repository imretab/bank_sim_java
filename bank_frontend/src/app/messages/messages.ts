import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Auth } from '../auth';
@Component({
  selector: 'app-messages',
  imports: [],
  templateUrl: './messages.html',
  styleUrl: './messages.css'
})
export class Messages {
  constructor(private http: HttpClient, private auth: Auth){}
  ngOnInit(){
    let id = this.auth.getLoggedInUser()?.jti;
    this.http.get(`http://localhost:8080/api/messages/${id}`).subscribe({
      next: (resp) =>{
        console.log(resp);
      }
    });
  }
}
