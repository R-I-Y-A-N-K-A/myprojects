import { Component, Input, OnInit } from '@angular/core';
import { BankingService } from '../../services/banking.service';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
 
@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css'],
  standalone: true,
  imports: [FormsModule, CommonModule]
})
export class DepositComponent{
  accountNumber: string = '';
  // @Input() account:any=null;
  // // @Input() account!: { accountNumber: string; accountHolder: string; balance: number };
  amount: number = 0;
  message: string = '';
 
  constructor(private bankingService: BankingService, private router: Router
    , private route: ActivatedRoute
  ) {}
 
  ngOnInit(): void{
    this.route.queryParams.subscribe(params =>{
      this.accountNumber=params['accountNumber'] || '';
    });
  }

  depositMoney(): void {
    this.bankingService.depositMoney(this.accountNumber, this.amount).subscribe(
      () => {
        this.message = `Successfully deposited ${this.amount} to account ${this.accountNumber}`;
        setTimeout(() => {
          this.router.navigate(['/accounts']);
        }, 2000);
      },
      (error) => console.error('Deposit failed:', error)
    );
}
 
  // depositMoney():void{
  //   if(!this.account || this.account.accountNumber){
  //     this.message='No account selected';
  //     return;
  //   }

  //   this.bankingService.depositMoney(this.account.accountNumber, this.amount).subscribe(
  //     ()=> {
  //       this.message= `Successfully deposited ${this.amount} to account ${this.account.accountNumber}`;
  //       setTimeout(() => {
  //         this.router.navigate(['/accounts']);
  //       }, 2000);
  //     },
  //     (error) => {
  //       console.error('Deposit failed:', error);
  //       this.message='Deposit failed';
  //         }
  //       );
  //     }
    
  
  // goToAccountList(): void {
  //   this.router.navigate(['/accounts']);
  // }

 
  // goToBalanceInquiry(): void {
  //   this.router.navigate(['/balance-inquiry'], { queryParams: { accountNumber: this.accountNumber } });
  // }
}
 