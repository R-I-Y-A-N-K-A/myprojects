import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BankingService } from '../../services/banking.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
 
@Component({
  selector: 'app-balance-inquiry',
  templateUrl: './balance-inquiry.component.html',
  styleUrls: ['./balance-inquiry.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class BalanceInquiryComponent implements OnInit {
  accountNumber: string = '';
  balance: number | null = null;
  errorMessage: string = '';
 
  constructor(private route: ActivatedRoute, private bankingService: BankingService, private router: Router) {}
 
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.accountNumber = params['accountNumber'] || '';
      if (this.accountNumber) {
        this.checkBalance();
      }
    });
  }
 
  checkBalance(): void {
    this.bankingService.getBalance(this.accountNumber).subscribe(
      (account) => {
        this.balance = account.balance;
        this.errorMessage = '';
      },
      (error) => {
        this.balance = null;
        this.errorMessage = 'Account not found or error fetching balance';
        console.error('Balance inquiry failed:', error);
      }
    );
  }

  goBack(): void{
    this.router.navigate(['/accounts']);
  }
}
 