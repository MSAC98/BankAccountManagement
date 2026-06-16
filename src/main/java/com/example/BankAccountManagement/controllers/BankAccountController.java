package com.example.BankAccountManagement.controllers;

import com.example.BankAccountManagement.entities.BankAccount;
import com.example.BankAccountManagement.services.BankAccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers/{customerId}")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @PostMapping(value = "/accounts", consumes = "application/json")
    public ResponseEntity<BankAccount> createAccount(@PathVariable int customerId, @RequestBody BankAccount account,
                                                     HttpServletRequest request) {
        BankAccount savedAccount = bankAccountService.createBankAccount(customerId, account.getAccountType(), account.getAccountBalance());

        if (savedAccount != null) {
            URI location = ServletUriComponentsBuilder.fromRequestUri(request)
                    .buildAndExpand(savedAccount.getAccountId())
                    .toUri();
            return ResponseEntity.created(location).body(savedAccount);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating bank account");
        }
    }

    @GetMapping(value = "accounts", produces = "application/json")
    public ResponseEntity<List<BankAccount>> getAccountsByCustomerId(@PathVariable int customerId) {
        List<BankAccount> bankAccounts = bankAccountService.getBankAccountsByCustomerId(customerId);
        return ResponseEntity.ok(bankAccounts);
    }

    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable int accountId) {
        boolean deleted = bankAccountService.deleteBankAccount(accountId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/accounts")
    public ResponseEntity<Void> deleteAccounts(@PathVariable int customerId) {
        boolean deleted = bankAccountService.deleteBankAccounts(customerId);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
