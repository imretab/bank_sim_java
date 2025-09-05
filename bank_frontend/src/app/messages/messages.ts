import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Auth } from '../auth';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-messages',
  imports: [CommonModule],
  templateUrl: './messages.html',
  styleUrl: './messages.css'
})
export class Messages implements OnInit {
  constructor(private http: HttpClient){}
  data: any[] = [];
  ngOnInit(){
    this.http.get<any[]>(`http://localhost:8080/api/messages`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('accessToken')}`
      }}).subscribe({
      next: (resp) =>{
        this.data = resp;
        console.log(this.data)
      }
    });
  }
}
