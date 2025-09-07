import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Auth } from '../auth';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private balanceURL = "http://localhost:8080/api/balance";
  private transactionURL = "http://localhost:8080/api/transaction";
  constructor(private http: HttpClient, private auth: Auth){}

  private getHeaders(): HttpHeaders {
    return new HttpHeaders().set('Authorization', `Bearer ${this.auth.getToken()}`);
  }

  getBalance(){
    return this.http.get(this.balanceURL,{headers: this.getHeaders()});
  }
  createTransaction(transaction : any){
    return this.http.post<{message:string}>(this.transactionURL,transaction,{headers:this.getHeaders()});
  }
}
