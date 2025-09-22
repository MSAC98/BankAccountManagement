package com.example.BankAccountManagement.services;

import com.example.BankAccountManagement.entities.BankAccount;
import com.example.BankAccountManagement.entities.Customer;
import com.example.BankAccountManagement.repositories.AccountRepo;
import com.example.BankAccountManagement.repositories.CustomerRepo;
import org.hibernate.type.TrueFalseConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private CustomerRepo customerRepo;

    public BankAccount createBankAccount(int customerId, String accountType, int accountBalance) {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        BankAccount account = new BankAccount();
        account.setAccountType(accountType);
        account.setAccountBalance(accountBalance);
        account.setCustomer(customer);

        return accountRepo.save(account);
    }

    public Optional<BankAccount> getBankAccountById(int accountId) {
        return accountRepo.findById(accountId);
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
