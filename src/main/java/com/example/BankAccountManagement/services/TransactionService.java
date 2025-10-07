package com.example.BankAccountManagement.services;

import com.example.BankAccountManagement.entities.BankAccount;
import com.example.BankAccountManagement.entities.Transaction;
import com.example.BankAccountManagement.repositories.AccountRepo;
import com.example.BankAccountManagement.repositories.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private AccountRepo accountRepo;

    public Transaction createTransaction(int accountId, String type, int amount) {
        BankAccount account = accountRepo.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        Transaction transaction = new Transaction();
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setTimestamp(java.time.LocalDateTime.now());
        transaction.setBankAccount(account);

        return transactionRepo.save(transaction);
    }

    public Optional<Transaction> getTransactionById(int transactionId) {
        return transactionRepo.findById(transactionId);
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        return transactionRepo.findByBankAccountAccountID(accountId);
    }

    public boolean deleteTransaction(int transactionId) {
        return transactionRepo.findById(transactionId)
                .map(transaction -> {
                    transactionRepo.deleteById(transactionId);
                    return true;
                })
                .orElse(false);
    }

    public boolean deleteTransactions(int accountId) {
        List<Transaction> accountTransactions = transactionRepo.findByBankAccountAccountID(accountId);

        if (accountTransactions.isEmpty()) {
            return false;
        }
        else {
            transactionRepo.deleteAll(accountTransactions);
            return true;
        }
    }

}
