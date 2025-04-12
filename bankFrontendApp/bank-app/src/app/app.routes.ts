import { Routes } from '@angular/router';

import { AccountCreationComponent } from './components/account-creation/account-creation.component';
import { AccountListComponent } from './components/account-list/account-list.component';
import { DepositComponent } from './components/deposit/deposit.component';
import { WithdrawalComponent } from './components/withdrawal/withdrawal.component';
import { BalanceInquiryComponent } from './components/balance-inquiry/balance-inquiry.component';
import { BankDashboardComponent } from './components/bank-dashboard/bank-dashboard.component';
 
export const routes: Routes = [
    { path: 'accounts', component: AccountListComponent},
  { path: 'create-account', component: AccountCreationComponent },
  { path: 'deposit', component: DepositComponent},
  { path: 'withdraw', component: WithdrawalComponent},
  {path: 'balance-inquiry', component: BalanceInquiryComponent},
  {path: 'dashboard', component: BankDashboardComponent},
  { path: '', redirectTo: '/accounts', pathMatch: 'full' }
//   { path: '**', redirectTo: '/create-account' } // Catch-all route
];
 