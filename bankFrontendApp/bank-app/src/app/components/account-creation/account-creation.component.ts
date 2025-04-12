import { Component, EventEmitter, Output } from '@angular/core';
import { BankingService } from '../../services/banking.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { error } from 'console';
import { BankingPubSubService } from '../../services/banking-pub-sub.service';
// import { NgForm } from '@angular/forms';
 
@Component({
  selector: 'app-account-creation',
  templateUrl: './account-creation.component.html',
  styleUrls: ['./account-creation.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class AccountCreationComponent {
  account = {
    accountNumber: '',
    accountHolder: '',
    balance: 0
  };

  // @Output() accountCreated=new EventEmitter<any>();
 
  constructor(private bankingService: BankingService, private router: Router, private bankingPubSubService: BankingPubSubService) {}
 
  // createAccount(): void {
  //   this.bankingService.createAccount(this.account).subscribe(response => {
  //     console.log('Account Created:', response);
  //     this.router.navigate(['/accounts']); // Redirect to the accounts list
  //   },
  //   (error)=>{
  //     console.error('Error creating account:', error);
  //   }
  // );
  // }

  createAccount(): void {
    this.bankingService.createAccount(this.account).subscribe({
      next: (response) => {
        console.log('Account Created:', response);
        this.bankingPubSubService.publishAccountCreated(response);  // Notify the parent
        this.router.navigate(['/accounts']); // Navigate to account list
      },
      error: (error) => {
        console.error('Error creating account:', error);
      }
    });
  }
}
