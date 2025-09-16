package com.example.BankAccountManagement.repositories;

import com.example.BankAccountManagement.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepo extends CrudRepository<Customer, Integer> {
}
