package com.example.BankAccountManagement.repositories;

import com.example.BankAccountManagement.entities.BankAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepo extends CrudRepository<BankAccount, Integer> {
    List<BankAccount> findByCustomerId(int customerId);
}
