package com.example.demo.service;

// import java.util.LinkedList;
import java.util.List;
// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.BankAccount;
import com.example.demo.repository.BankRepository;

@Service
public class BankService {
    // List<BankAccount> accounts=new LinkedList<BankAccount>();

    @Autowired
    private BankRepository bankRepository;

    //Creating a new account
    // public String createAccount(BankAccount newAccount){
    //     for(BankAccount bankAccount : accounts){
    //         if(bankAccount.getAccountNumber().equals(newAccount.getAccountNumber())){
    //             return "Account number already exists!";
    //         }
    //     }
    //     accounts.add(newAccount);
    //     return "Account created successfully!";
    // }

    public String createAccount(BankAccount newAccount) {
        if (bankRepository.findAll().stream().anyMatch(account -> account.getAccountNumber().equals(newAccount.getAccountNumber()))) {
            return "Account number already exists!";
        }
        bankRepository.save(newAccount);
        return "Account created successfully!";
    }

    //Reading specific account details 
    // public BankAccount getAccount(String accountNo){
    //     for(BankAccount bankAccount:accounts){
    //         if(bankAccount.getAccountNumber().equals(accountNo)){
    //             return bankAccount;
    //         }
    //     }
    //     return null;
    // }

    public BankAccount getAccount(String accountNo) {

        return bankRepository.findAll().stream()
                .filter(account -> account.getAccountNumber().equals(accountNo))
                .findFirst()
                .orElse(null); 
    }

    //Reading all account details 
    public List<BankAccount> getAllAccounts(){
        return bankRepository.findAll();
    }

    //updating account holder name
    // public String updateAccountHolder(String accountNo, String newAccountHolder){
    //     for(BankAccount bankAccount:accounts){
    //         if(bankAccount.getAccountNumber().equals(accountNo)){
    //             bankAccount.setAccountHolder(newAccountHolder);
    //             return "Account holder updated successfully!";
    //         }           
    //     }
    //             return "Account not found!";
    // }

    public String updateAccountHolder(String accountNo, String newAccountHolder) {
        return bankRepository.findAll().stream()
                .filter(bankAccount -> bankAccount.getAccountNumber().equals(accountNo))
                .findFirst()
                .map(bankAccount -> {bankAccount.setAccountHolder(newAccountHolder);
                bankRepository.save(bankAccount);
                return "Account holder updated successfully!";
            })
            .orElse("Account not found!");
    }

    //depositing money
    // public String deposit(String accountNo, double amount){
    //     for(BankAccount bankAccount:accounts){
    //         if(bankAccount.getAccountNumber().equals(accountNo)){
    //             double balance=bankAccount.getBalance();
    //             if(amount>0){
    //                 balance+=amount;
    //                 bankAccount.setBalance(balance);
    //                 return "Deposited Rs. "+amount+ " successfully!";
    //             }
    //                 return "Please enter valid amount!";
    //         }           
    //     }
    //             return "Account not found!";
    // }

    public String deposit(String accountNo, double amount) {
        return bankRepository.findAll().stream()
                .filter(account -> account.getAccountNumber().equals(accountNo))
                .findFirst()
                .stream()
                .map(account -> {
                    if (amount <= 0) 
                        return "Please enter a valid amount!";
                        account.setBalance(account.getBalance() + amount);
                        bankRepository.save(account);
                        return "Deposited Rs. " + amount + " successfully!";
                    })
                    .findFirst()
                    .orElse("Account not found!");   
        }
    

    //withdrawing money
    // public String withdraw(String accountNo, double amount){
    //     for(BankAccount bankAccount:accounts){
    //         if(bankAccount.getAccountNumber().equals(accountNo)){
    //             double balance=bankAccount.getBalance();
    //             if(amount>0 && amount<=balance){
    //                 balance-=amount;
    //                 bankAccount.setBalance(balance);
    //                 return "Withdrawn Rs. "+amount+ " successfully!";
    //             } else if(amount>balance){
    //                 return "Insufficient balance!";
    //             } else{
    //                 return "Please enter valid amount!";
    //             }
    //         }           
    //     }
    //         return "Account not found!";
    // }

    public String withdraw(String accountNo, double amount) {
        return bankRepository.findAll().stream()
                .filter(account -> account.getAccountNumber().equals(accountNo))
                .findFirst()
                .map(account -> {
                    if (amount <= 0) {
                        return "Please enter a valid amount!";
                    }
                    if(amount>account.getBalance()){
                        return "Insufficient balance!";
                    }
                        account.setBalance(account.getBalance()-amount);
                        bankRepository.save(account);
                        return "Withdrawn Rs. " + amount + " successfully!";
                })
                .orElse("Account not found!");
    }

    //deleting an account
    // public String deleteAccount(String accountNo){
    //     for(BankAccount bankAccount:accounts){
    //         if(bankAccount.getAccountNumber().equals(accountNo)){
    //             accounts.remove(bankAccount);
    //             return "Account deleted successfully!";
    //         }
    //     }
    //     return "Account not found!";
    // }

    public String deleteAccount(String accountNo) {
        return bankRepository.findAll().stream()
                .filter(account -> account.getAccountNumber().equals(accountNo)) 
                .findFirst()
                .map(account -> {
                    bankRepository.delete(account);
                    return "Account deleted successfully!";
                })
                .orElse("Account not found!");
    }
}