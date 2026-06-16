package com.example.BankAccountManagement.controllers;

import com.example.BankAccountManagement.entities.BankAccount;
import com.example.BankAccountManagement.entities.Customer;
import com.example.BankAccountManagement.repositories.AccountRepo;
import com.example.BankAccountManagement.repositories.CustomerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BankAccountControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AccountRepo accountRepo;

    int customerId;

    @BeforeEach
    void setup() {
        customerRepo.deleteAll();
        accountRepo.deleteAll();

        Customer customer =new Customer();
        customer.setFirstName("Test");
        customer.setLastName("Customer");

        customerId = customerRepo.save(customer).getCustomerId();
    }

    @Test
    void createBankAccountForCustomer() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/accounts", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "accountType": "Checking",
                  "accountBalance": 1000
                }
            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountType").value("Checking"))
                .andExpect(jsonPath("$.accountBalance").value(1000));
    }

    @Test
    void getBankAccountsByCustomerId() throws Exception {
        BankAccount mockAccount1 = new BankAccount("Checking", 2500);
        BankAccount mockAccount2 = new BankAccount("Saving", 40000);
        mockAccount1.setCustomer(customerRepo.findById(customerId).orElseThrow());
        mockAccount2.setCustomer(customerRepo.findById(customerId).orElseThrow());

        accountRepo.save(mockAccount1);
        accountRepo.save(mockAccount2);

        mockMvc.perform(get("/customers/{customerId}/accounts", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountType").value("Checking"))
                .andExpect(jsonPath("$[0].accountBalance").value(2500))
                .andExpect(jsonPath("$[1].accountType").value("Saving"))
                .andExpect(jsonPath("$[1].accountBalance").value(40000));
    }

    @Test
    void deleteBankAccountByAccountId() throws Exception {
        BankAccount mockAccount = new BankAccount("Checking", 1200);
        mockAccount.setCustomer(customerRepo.findById(customerId).orElseThrow());

        accountRepo.save(mockAccount);
        int accountId = mockAccount.getAccountId();

        mockMvc.perform(delete("/customers/{customerId}/accounts/{accountId}", customerId, accountId))
                .andExpect(status().isNoContent());

        assertFalse(accountRepo.findById(accountId).isPresent());
    }
}
