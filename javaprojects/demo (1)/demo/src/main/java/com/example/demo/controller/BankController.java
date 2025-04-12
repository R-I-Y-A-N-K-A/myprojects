package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.BankAccount;
import com.example.demo.service.BankService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bank")
@Tag(name = "Bank Account API", description = "Operations related to Bank Account Management")

public class BankController {
    @Autowired
    private BankService bankService;

    @Operation(summary = "New Bank Account Creation", 
            description = "Creating a bank account with attributes like account number, account holder name and balance initiated to 0.0")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Bank Account created successfully"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    @PostMapping("/account/create")
    public ResponseEntity<String> createAccount(@Valid @RequestBody BankAccount bankAccount){
        return ResponseEntity.status(201).body(bankService.createAccount(bankAccount));
    }

    @Operation(summary = "Fetch all bank accounts", description = "Fetches a list of all bank accounts.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved all bank accounts",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccount.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/accounts")
    public ResponseEntity<List<BankAccount>> getAllAccounts(){
        return ResponseEntity.status(200).body(bankService.getAllAccounts());
    }

    @Operation(summary = "Get a bank account by account number", description = "Fetches details of a specific bank account by its account number.")
    @Parameter(name = "accountNumber", description = "The account number of the bank account", required = true)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved the bank account",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = BankAccount.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<BankAccount> getAccount(@PathVariable String accountNumber){
        return ResponseEntity.status(200).body(bankService.getAccount(accountNumber));
    }

    @Operation(summary = "Update account holder", description = "Updates the account holder for a specific account.")
    @Parameters(value = {
        @Parameter(name = "accountNumber", description = "The account number of the bank account", required = true),
        @Parameter(name = "newAccountHolder", description = "Updated account holder name", required = true)
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully updated the account holder"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/account/{accountNumber}/holder/{newAccountHolder}")
    public ResponseEntity<String> updateAccountHolder(@PathVariable String accountNumber, @PathVariable String newAccountHolder){
        return ResponseEntity.status(200).body(bankService.updateAccountHolder(accountNumber, newAccountHolder));
    }

    @Operation(summary = "Deposit money into account", description = "Deposits the specified amount into a bank account.")
    @Parameters(value = {
        @Parameter(name = "accountNumber", description = "The account number of the bank account", required = true),
        @Parameter(name = "amount", description = "The amount to be deposited")
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully deposited to the account"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/account/{accountNumber}/deposit/{amount}")
    public ResponseEntity<String> deposit(@PathVariable String accountNumber, @PathVariable double amount){
        return ResponseEntity.status(200).body(bankService.deposit(accountNumber, amount));
    }
    
    @Operation(summary = "Withdraw money from account", description = "Withdraws the specified amount from a bank account.")
    @Parameters(value = {
        @Parameter(name = "accountNumber", description = "The account number of the bank account", required = true),
        @Parameter(name = "amount", description = "The amount to be withdrawn")
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successfully withdrawn from the account"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/account/{accountNumber}/withdrawal/{amount}")
    public ResponseEntity<String> withdraw(@PathVariable String accountNumber, @PathVariable double amount){
        return ResponseEntity.status(200).body(bankService.withdraw(accountNumber, amount));
    }

    @Operation(summary = "Delete a bank account", description = "Deletes a bank account by its account number.")
    @Parameter(name = "accountNumber", description = "The account number of the bank account", required = true)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully deleted the bank account"),
        @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/account/{accountNumber}/delete")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber){
        return ResponseEntity.status(200).body(bankService.deleteAccount(accountNumber));
    }
}
