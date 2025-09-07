import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Auth } from '../auth';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private getProfileURL = "http://localhost:8080/api/profile";
  private putProfileURL = "http://localhost:8080/api/updateProfile";
  constructor(private http: HttpClient, private auth: Auth){}
  private getHeaders(): HttpHeaders {
    return new HttpHeaders().set('Authorization', `Bearer ${this.auth.getToken()}`);
  }

  getProfileValues(): Observable<any>{
    return this.http.get(this.getProfileURL,{headers: this.getHeaders()});
  }

  putProfileValues(update: any){
    return this.http.put<{message:string}>(this.putProfileURL,update,{headers: this.getHeaders()});
  }
}
