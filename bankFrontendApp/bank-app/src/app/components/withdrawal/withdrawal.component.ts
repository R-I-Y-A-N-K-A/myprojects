import { Component, OnInit } from '@angular/core';
import { BankingService } from '../../services/banking.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
 
@Component({
  selector: 'app-withdrawal',
  templateUrl: './withdrawal.component.html',
  styleUrls: ['./withdrawal.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class WithdrawalComponent implements OnInit{
  accountNumber: string = '';
  amount: number = 0;
  message: string = '';
 
  constructor(private bankingService: BankingService, private router: Router, private route: ActivatedRoute) {}
  
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.accountNumber = params['accountNumber'] || '';
    });
  }
 
  withdrawMoney(): void {
    this.bankingService.getBalance(this.accountNumber).subscribe(
      (account) => {
        this.bankingService.withdrawMoney(this.accountNumber, this.amount).subscribe(
          () => {
            this.message = `Successfully withdrew ₹${this.amount} from account ${this.accountNumber}`;
            setTimeout(() => {
              this.router.navigate(['/accounts']);
            }, 2000);
          },
          (error) => {
            this.message = 'Withdrawal failed: ' + error.message;
            console.error('Withdraw failed:', error);
          }
        );
      },
      (error) => {
        this.message = 'Account not found';
        console.error('Account not found:', error);
      }
    );
  }
 
  goToAccountList(): void {
    this.router.navigate(['/accounts']);
  }
}
 