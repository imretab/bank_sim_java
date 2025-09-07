import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { TransactionService } from '../services/transaction-service';

declare var bootstrap: any;

@Component({
  selector: 'app-transaction-page',
  imports: [ReactiveFormsModule],
  templateUrl: './transaction-page.component.html',
  styleUrl: './transaction-page.component.css'
})
export class TransactionPageComponent implements OnInit{
  @ViewChild('messageModal') messageModal!: ElementRef;
  
  balance : any;
  message: any;
  title: any;
  private fetchBalance(){
    this.transactionService.getBalance().subscribe({
      next: (resp) =>{
        this.balance = resp;
      }
    })
  }
  ngOnInit(){
    this.fetchBalance();
  }
  showModal(message:string,title:string){
    this.message = message;
    this.title=title;
    const modal = new bootstrap.Modal(this.messageModal.nativeElement);
    modal.show();
  }
  transaction = new FormGroup({
    accountNumber: new FormControl('',[Validators.required]),
    amount: new FormControl('',[Validators.required])
  });
  constructor(private transactionService: TransactionService){}
  formSubmit(){
    let accNum = this.transaction.controls.accountNumber?.value;
    let amount = this.transaction.controls.amount?.value;
    let tryTransaction = {accNum, amount};
    this.transactionService.createTransaction(tryTransaction).subscribe({
      next: (response)=>{
        console.log(response);
        this.transaction.reset();
        this.fetchBalance();
        this.showModal(response.message,"Success message");
      },
      error:(err)=>{
        console.error(err);
        this.showModal(err.error.message,"Error message");
      }
    })
  }
}
