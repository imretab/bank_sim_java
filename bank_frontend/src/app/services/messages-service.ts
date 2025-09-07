import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Auth } from '../auth';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessagesService {
  private messagesURL = "http://localhost:8080/api/messages";
  constructor(private http: HttpClient, private auth: Auth){}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders().set('Authorization', `Bearer ${this.auth.getToken()}`);
  }

  getLoggedInMessages(): Observable<any[]>{
    return this.http.get<any[]>(this.messagesURL,{headers:this.getHeaders()});
  }
}
