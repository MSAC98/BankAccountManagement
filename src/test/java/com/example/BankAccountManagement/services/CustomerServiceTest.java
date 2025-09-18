package com.example.BankAccountManagement.services;

import com.example.BankAccountManagement.entities.Customer;
import com.example.BankAccountManagement.repositories.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepo customerRepo;

    @Test
    void testCreateCustomer() {
        int customerId = 1;
        Customer mockCustomer = new Customer("Michael", "Jordan");
        mockCustomer.setId(customerId);

        when(customerRepo.save(mockCustomer)).thenReturn(mockCustomer);

        Customer result = customerService.saveCustomer(mockCustomer);

        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("Michael", result.getFirstName());
        assertEquals("Jordan", result.getLastName());
    }

    //TODO
    @Test
    void testGetAllCustomers() {
    }

    @Test
    void testGetCustomerById() {
        int customerId = 1;
        Customer mockCustomer = new Customer("John", "Doe");
        mockCustomer.setId(customerId);

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        Optional<Customer> result = customerService.getCustomerById(customerId);

        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getId());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
    }

    @Test
    void testUpdateCustomer() {
        int customerId = 1;
        Customer existingCustomer = new Customer("Alice", "Johnson");
        existingCustomer.setId(customerId);

        Customer updatedCustomer = new Customer("Aline", "Doe");

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepo.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Customer> result = customerService.updateCustomer(updatedCustomer, customerId);

        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getId());
        assertEquals("Aline", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
    }

    @Test
    void testUpdateCustomer_NotFound() {
    }

    @Test
    void testDeleteCustomerById() {
        int customerId = 1;
        Customer mockCustomer = new Customer("Alicia", "Jobs");
        mockCustomer.setId(customerId);

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        boolean result = customerService.deleteCustomer(customerId);

        assertTrue(result);
        verify(customerRepo, times(1)).deleteById(customerId);
    }

    // TODO
    @Test
    void testDeleteCustomer_NotFound() {
    }
}
