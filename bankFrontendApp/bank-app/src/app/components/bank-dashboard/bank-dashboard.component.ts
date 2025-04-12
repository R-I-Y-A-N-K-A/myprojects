import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountCreationComponent } from '../account-creation/account-creation.component';
// import { DepositComponent } from '../deposit/deposit.component';
// import { WithdrawalComponent } from '../withdrawal/withdrawal.component';
// import { BalanceInquiryComponent } from '../balance-inquiry/balance-inquiry.component';
// import { AccountListComponent } from '../account-list/account-list.component';
import { BankingPubSubService } from '../../services/banking-pub-sub.service';
// import { Account } from '../../models/account.model';
 
@Component({
  selector: 'app-bank-dashboard',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    AccountCreationComponent
    // DepositComponent
    // WithdrawalComponent,
    // BalanceInquiryComponent,
    // AccountListComponent
  ],
  templateUrl: './bank-dashboard.component.html',
  styleUrl: './bank-dashboard.component.css'
})
export class BankDashboardComponent implements OnInit{
  accountState: any = null;
 
  constructor(private bankingPubSubService: BankingPubSubService) {}
  
  ngOnInit(): void {
    console.log('BankDashboardComponent initialized');
    this.bankingPubSubService.accountCreated$.subscribe(account => {
      this.accountState = account;
      console.log('Account received in dashboard:', account);
    });
    // throw new Error('Method not implemented.');
  }
}
 