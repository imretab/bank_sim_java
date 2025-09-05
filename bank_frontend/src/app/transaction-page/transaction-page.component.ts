import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-transaction-page',
  imports: [ReactiveFormsModule],
  templateUrl: './transaction-page.component.html',
  styleUrl: './transaction-page.component.css'
})
export class TransactionPageComponent {
  transaction = new FormGroup({
    accountNumber: new FormControl('',[Validators.required]),
    amount: new FormControl('',[Validators.required])
  });
  constructor(private http: HttpClient){}
  formSubmit(){
    let accNum = this.transaction.controls.accountNumber?.value;
    let amount = this.transaction.controls.amount?.value;
    let tryTransaction = {accNum, amount};
    this.http.post(`http://localhost:8080/api/transaction`,tryTransaction,{
      headers: {
        Authorization: `Bearer ${localStorage.getItem('accessToken')}`
      }
    }).subscribe({
      next:(response)=>{
        console.log(response)
      },
      error(err) {
          console.error(err);
      },
    });
  }
}
