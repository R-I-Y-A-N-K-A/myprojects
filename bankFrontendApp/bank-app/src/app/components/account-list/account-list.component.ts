import { Component, OnInit } from '@angular/core';
import { BankingService } from '../../services/banking.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
 
@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css'],
  standalone: true,
  imports: [CommonModule]
})
export class AccountListComponent implements OnInit {
  accounts: any[] = []; // Holds account data
 
  constructor(private bankingService: BankingService, private router: Router) {}
 
  // ngOnInit(): void {
  //   this.bankingService.getAccounts().subscribe(
  //     (response) => {
  //       this.accounts = response; // Store fetched accounts
  //     },
  //     (error) => {
  //       console.error('Error fetching accounts:', error);
  //     }
  //   );
  // }
  ngOnInit(): void {
    this.loadAccounts();
  }
   
  loadAccounts(): void {
    this.bankingService.getAccounts().subscribe(
      (data) => (this.accounts = data),
      (error) => console.error('Error loading accounts:', error)
    );
  }
 
  goToCreateAccount(): void {
    this.router.navigate(['/create-account']);
  }

  goToDeposit(accountNumber: string){
    this.router.navigate(['/deposit'], {queryParams:{accountNumber}});
  }

  goToWithdrawal(accountNumber: string){
    this.router.navigate(['/withdraw'], {queryParams:{accountNumber}});
  }

  // goToBalanceInquiry(accountNumber: string){
  //   this.router.navigate(['/balance-inquiry'], {queryParams: {accountNumber}});
  // }

  goToBalanceInquiry(){
    this.router.navigate(['/balance-inquiry']);
  }

  // deleteAccount(accountNumber: string): void {
  //   this.bankingService.deleteAccount(accountNumber).subscribe({
  //     next: () => {
  //       console.log(`Account ${accountNumber} deleted successfully`);
  //       this.accounts = this.accounts.filter(account => account.accountNumber !== accountNumber); // Remove account from list
  //     },
  //     error: (error) => console.error('Error deleting account:', error)
  //   });
  // }

  deleteAccount(account: any): void {
    console.log(`Attempting to delete account:`, account); // Debugging log
  
    if (!account?.id) {
      console.error('Error: Account ID is undefined');
      return;
    }
  
    this.bankingService.deleteAccount(account.id).subscribe({
      next: () => {
        console.log(`Account with ID ${account.id} deleted successfully`);
        this.accounts = this.accounts.filter(acc => acc.id !== account.id);
      },
      error: (error) => console.error('Error deleting account:', error)
    });
  }
}
 