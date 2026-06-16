package com.example.BankAccountManagement.controllers;

import com.example.BankAccountManagement.entities.Customer;
import com.example.BankAccountManagement.repositories.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CustomerControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CustomerRepo customerRepo;

    @Test
    void createCustomer() throws Exception {
        mockMvc.perform(post("/customers")
                .contentType("application/json")
                .content("{\"firstName\":\"Alicia\",\"lastName\":\"Jobs\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Alicia"))
                .andExpect(jsonPath("$.lastName").value("Jobs"));
    }

    @Test
    void getCustomerByIdReturnsCustomerWhenExists() throws Exception {
        Customer mockCustomer = customerRepo.save(new Customer("Alicia", "Jobs"));

        mockMvc.perform(get("/customers/{id}", mockCustomer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alicia"))
                .andExpect(jsonPath("$.lastName").value("Jobs"));
    }

    @Test
    void updateCustomerById() throws Exception {
        Customer mockCustomer = customerRepo.save(new Customer("Aline", "Doe"));
        int customerId = mockCustomer.getCustomerId();

        mockMvc.perform(put("/customers/{id}", customerId)
                .contentType("application/json")
                .content("{\"firstName\":\"Jessica\",\"lastName\":\"Johnson\"}"))
                .andExpect(status().isOk());

        Customer updated = customerRepo.findById(customerId).orElseThrow();
        assertEquals("Jessica", updated.getFirstName());
        assertEquals("Johnson", updated.getLastName());
    }

    @Test
    void deleteCustomerById() throws Exception {
        Customer mockCustomer = customerRepo.save(new Customer("Bob", "Smith"));
        int customerId = mockCustomer.getCustomerId();

        mockMvc.perform(delete("/customers/{id}", customerId))
                .andExpect(status().isNoContent());

        assertFalse(customerRepo.findById(customerId).isPresent());
    }
}
