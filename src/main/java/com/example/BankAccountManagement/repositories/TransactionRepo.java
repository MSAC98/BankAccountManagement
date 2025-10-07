package com.example.BankAccountManagement.repositories;

import com.example.BankAccountManagement.entities.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepo extends CrudRepository<Transaction, Integer> {
    List<Transaction> findByBankAccountAccountID(int accountID);
}
