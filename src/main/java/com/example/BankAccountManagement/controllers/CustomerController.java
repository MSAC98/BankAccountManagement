package com.example.BankAccountManagement.controllers;

import com.example.BankAccountManagement.entities.Customer;
import com.example.BankAccountManagement.repositories.CustomerRepo;
import com.example.BankAccountManagement.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Iterable<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)                  // 200 OK if found
                .orElse(ResponseEntity.notFound().build()); // 404 Not Found if missing
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }
}
