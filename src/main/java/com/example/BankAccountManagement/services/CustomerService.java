package com.example.BankAccountManagement.services;

import com.example.BankAccountManagement.entities.Customer;
import com.example.BankAccountManagement.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    public Iterable<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Optional<Customer> getCustomerById(int id) {
        return customerRepo.findById(id);
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public void deleteCustomer(int id) {
        customerRepo.deleteById(id);
    }
}
