import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
 
@Injectable({
  providedIn: 'root'
})
export class BankingPubSubService {
  private accountCreatedSource = new Subject<any>();
  // private balanceUpdatedSource = new Subject<any>();
  // private accountSelectedSource = new Subject<string>();
 
  // Observables
  accountCreated$ = this.accountCreatedSource.asObservable();
  // balanceUpdated$ = this.balanceUpdatedSource.asObservable();
  // accountSelected$ = this.accountSelectedSource.asObservable();
 
  // Emitters
  publishAccountCreated(account: any): void {
    console.log('Publishing Account Created:', account);
    this.accountCreatedSource.next(account);
  }
 
  // notifyBalanceUpdated(data: any): void {
  //   this.balanceUpdatedSource.next(data);
  // }
 
  // selectAccount(accountNumber: string): void {
  //   this.accountSelectedSource.next(accountNumber);
  // }
}
 