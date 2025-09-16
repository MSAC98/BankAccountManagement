package com.example.BankAccountManagement.services;

import com.example.BankAccountManagement.entities.Account;
import com.example.BankAccountManagement.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    public Account createAccount(int customerId, String type, int balance) {
        Account account = new Account();
        account.setCustomerId(customerId);
        account.setType(type);
        account.setBalance(balance);
        return accountRepo.save(account);
    }

    public List<Account> getAccountsByCustomerId(int customerId) {
        return accountRepo.findByCustomerId(customerId);
    }
}
