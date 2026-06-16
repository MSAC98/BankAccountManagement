package com.example.BankAccountManagement.services;

import com.example.BankAccountManagement.entities.Customer;
import com.example.BankAccountManagement.repositories.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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
        mockCustomer.setCustomerId(customerId);

        when(customerRepo.save(mockCustomer)).thenReturn(mockCustomer);

        Customer result = customerService.saveCustomer(mockCustomer);

        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals("Michael", result.getFirstName());
        assertEquals("Jordan", result.getLastName());
    }

    @Test
    void testGetAllCustomers() {
        Customer mockCustomer1 = new Customer("Michael", "Jordan");
        Customer mockCustomer2 = new Customer("John", "Doe");

        List<Customer> customerList = new ArrayList<>();
        customerList.add(mockCustomer1);
        customerList.add(mockCustomer2);

        when(customerRepo.findAll()).thenReturn(customerList);

        Iterable<Customer> result = customerService.getAllCustomers();

        List<Customer> resultList = new ArrayList<>();
        result.forEach(resultList::add);

        assertEquals(2, resultList.size());
        assertEquals("Michael", resultList.get(0).getFirstName());
        assertEquals("Jordan", resultList.get(0).getLastName());
        assertEquals("John", resultList.get(1).getFirstName());
        assertEquals("Doe", resultList.get(1).getLastName());
    }

    @Test
    void testGetCustomerById() {
        int customerId = 1;
        Customer mockCustomer = new Customer("John", "Doe");
        mockCustomer.setCustomerId(customerId);

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        Optional<Customer> result = customerService.getCustomerById(customerId);

        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getCustomerId());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
    }

    @Test
    void testUpdateCustomer() {
        int customerId = 1;
        Customer existingCustomer = new Customer("Alice", "Johnson");
        existingCustomer.setCustomerId(customerId);

        Customer updatedCustomer = new Customer("Aline", "Doe");

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(existingCustomer));
        when(customerRepo.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<Customer> result = customerService.updateCustomer(updatedCustomer, customerId);

        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getCustomerId());
        assertEquals("Aline", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
    }

    @Test
    void testUpdateCustomer_NotFound() {
        int customerId = 99;
        Customer updatedCustomer = new Customer("Aline", "Doe");

        when(customerRepo.findById(customerId)).thenReturn(Optional.empty());

        Optional<Customer> result = customerService.updateCustomer(updatedCustomer, customerId);

        verify(customerRepo, never()).save(any(Customer.class));
        assertTrue(result.isEmpty());
    }

    @Test
    void testDeleteCustomerById() {
        int customerId = 1;
        Customer mockCustomer = new Customer("Alicia", "Jobs");
        mockCustomer.setCustomerId(customerId);

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(mockCustomer));

        boolean result = customerService.deleteCustomer(customerId);

        assertTrue(result);
        verify(customerRepo, times(1)).deleteById(customerId);
    }

    @Test
    void testDeleteCustomer_NotFound() {
        int customerId = 99;

        when(customerRepo.findById(customerId)).thenReturn(Optional.empty());

        boolean result = customerService.deleteCustomer(customerId);

        verify(customerRepo, never()).deleteById(anyInt());
        assertFalse(result);
    }
}
