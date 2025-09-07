import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Auth } from '../auth';
import { CommonModule } from '@angular/common';
import { MessagesService } from '../services/messages-service';
@Component({
  selector: 'app-messages',
  imports: [CommonModule],
  templateUrl: './messages.html',
  styleUrl: './messages.css'
})
export class Messages implements OnInit {
  constructor(private messageService: MessagesService){}
  data: any[] = [];
  ngOnInit(){
    this.messageService.getLoggedInMessages().subscribe({
      next:(response)=>{
        this.data = response;
      }
    })
  }
}
