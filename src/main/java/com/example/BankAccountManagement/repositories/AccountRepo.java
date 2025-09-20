package com.example.BankAccountManagement.repositories;

import com.example.BankAccountManagement.entities.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepo extends CrudRepository<Account, Integer> {
    List<Account> findByCustomerId(int customerId);
}
