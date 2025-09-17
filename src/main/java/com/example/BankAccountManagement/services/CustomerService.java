package com.example.BankAccountManagement.services;

import com.example.BankAccountManagement.entities.Customer;
import com.example.BankAccountManagement.repositories.CustomerRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    public Optional<Customer> update(Customer customer, int id) {
        return customerRepo.findById(id).map(currentCustomer -> {
            currentCustomer.setFirstName(customer.getFirstName());
            currentCustomer.setLastName(customer.getLastName());
            return customerRepo.save(currentCustomer);
        });
    }

    public void deleteCustomer(int id) {
        customerRepo.deleteById(id);
    }
}
