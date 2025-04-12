package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class BankAccount {
    @Id
    private String accountNumber;

    @NotBlank(message = "Account holder name cannot be blank")
    @Size(min = 3, message = "Account holder name should have at least 3 characters")
    private String accountHolder;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be valid and must contain '@' symbol" )
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
    
    private double balance;

    public BankAccount() {
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BankAccount(String accountNumber, String accountHolder){
        this.accountNumber=accountNumber;
        this.accountHolder=accountHolder;
        this.balance=0.0;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    
    public String getAccountHolder(){
        return accountHolder;
    }
    
    public void setAccountHolder(String accountHolder){
        this.accountHolder=accountHolder;
    }
    
    public double getBalance(){
        return balance;
    }
    
    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
        result = prime * result + ((accountHolder == null) ? 0 : accountHolder.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
        long temp;
        temp = Double.doubleToLongBits(balance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BankAccount other = (BankAccount) obj;
        if (accountNumber == null) {
            if (other.accountNumber != null)
                return false;
        } else if (!accountNumber.equals(other.accountNumber))
            return false;
        if (accountHolder == null) {
            if (other.accountHolder != null)
                return false;
        } else if (!accountHolder.equals(other.accountHolder))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (phoneNumber == null) {
            if (other.phoneNumber != null)
                return false;
        } else if (!phoneNumber.equals(other.phoneNumber))
            return false;
        if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
            return false;
        return true;
    }
}