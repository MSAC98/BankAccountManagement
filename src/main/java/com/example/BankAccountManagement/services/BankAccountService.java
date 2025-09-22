package com.example.BankAccountManagement.services;

import com.example.BankAccountManagement.entities.BankAccount;
import com.example.BankAccountManagement.repositories.AccountRepo;
import org.hibernate.type.TrueFalseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankAccountService {

    @Autowired
    private AccountRepo accountRepo;

    public BankAccount createBankAccount(int customerId, String accountType, int accountBalance) {
        BankAccount account = new BankAccount();
        account.setCustomerId(customerId);
        account.setAccountType(accountType);
        account.setAccountBalance(accountBalance);
        return accountRepo.save(account);
    }

    public List<BankAccount> getBankAccountsByCustomerId(int customerId) {
        return accountRepo.findByCustomerId(customerId);
    }

    public boolean deleteBankAccount(int accountId) {
        return accountRepo.findById(accountId)
                        .map(bankAccount -> {
                            accountRepo.deleteById(accountId);
                            return true;
                        })
                                .orElse(false);
    }

    public boolean deleteBankAccounts(int customerId) {
        List<BankAccount> customerAccounts = accountRepo.findByCustomerId(customerId);

        if (customerAccounts.isEmpty()) {
            return false;
        }
        else {
            accountRepo.deleteAll(customerAccounts);
            return true;
        }
    }
}
