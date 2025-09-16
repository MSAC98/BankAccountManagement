package com.example.BankAccountManagement.controllers;

import com.example.BankAccountManagement.entities.Account;
import com.example.BankAccountManagement.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{customerId}")
    public Account createAccount(@PathVariable int customerId, @RequestBody Account account) {
        return accountService.createAccount(customerId, account.getType(), account.getBalance());
    }

    @GetMapping("/customer/{customerId}")
    public List<Account> getAccountsByCustomerId(@PathVariable int customerId) {
        return accountService.getAccountsByCustomerId(customerId);
    }
}
