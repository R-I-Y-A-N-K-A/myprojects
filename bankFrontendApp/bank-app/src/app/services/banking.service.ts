import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable, switchMap } from 'rxjs';
 
@Injectable({
  providedIn: 'root'
})
export class BankingService {
  private apiUrl = 'http://localhost:3000/accounts'; // JSON Server URL
 
  constructor(private http: HttpClient) {}
 
  getAccounts(): Observable<any> {
    return this.http.get<any>(this.apiUrl);
  }
 
  createAccount(account: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, account);
  }
 
  // depositMoney(accountNumber: string, amount: number, currentBalance: number): Observable<any> {
  //   const updatedBalance = currentBalance + amount;
  //   return this.http.patch<any>(`${this.apiUrl}/${accountNumber}`, { balance: updatedBalance });
  // }

  depositMoney(accountNumber: string, amount: number): Observable<any> {
    return this.http.get<any[]>(`${this.apiUrl}?accountNumber=${accountNumber}`).pipe(
      switchMap((accounts) => {
        if (accounts.length === 0) {
          throw new Error('Account not found');
        }
        const account = accounts[0]; 
        const updatedBalance = account.balance + amount;
return this.http.patch(`${this.apiUrl}/${account.id}`, { balance: updatedBalance });
      })
    );
}
   
  withdrawMoney(accountNumber: string, amount: number): Observable<any> {
    return this.http.get<any[]>(`${this.apiUrl}?accountNumber=${accountNumber}`).pipe(
      switchMap((accounts) => {
        if (accounts.length === 0) {
        throw new Error('Account not found');
        }
 
      const account = accounts[0];
      if (amount > account.balance) {
        throw new Error('Insufficient balance');
      }
 
      const updatedBalance = account.balance - amount;
      return this.http.patch(`${this.apiUrl}/${account.id}`, { balance: updatedBalance });
    })
  );
}
 
getBalance(accountNumber: string): Observable<any> {
  return this.http.get<any[]>(`${this.apiUrl}?accountNumber=${accountNumber}`).pipe(
    map((accounts) => {
      if (accounts.length === 0) {
        throw new Error('Account not found');
      }
      return accounts[0]; // Return the first matched account
    })
  );
}

// deleteAccount(accountNumber: string): Observable<any>{
//   return this.http.delete<any>(`${this.apiUrl}/${accountNumber}`);
//   }

  deleteAccount(accountId: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${accountId}`);
  }
}
 