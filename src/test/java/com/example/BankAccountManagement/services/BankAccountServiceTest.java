package com.example.BankAccountManagement.services;

import com.example.BankAccountManagement.entities.BankAccount;
import com.example.BankAccountManagement.entities.Customer;
import com.example.BankAccountManagement.repositories.AccountRepo;
import com.example.BankAccountManagement.repositories.CustomerRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @InjectMocks
    private BankAccountService bankAccountService;

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Test
    void testBankAccountCustomer() {
        int customerId = 1;
        Customer mockCustomer = new Customer("Michael", "Jordan");
        mockCustomer.setCustomerId(customerId);

        BankAccount mockAccount = new BankAccount();
        mockAccount.setAccountType("Checking");
        mockAccount.setAccountBalance(1000);
        mockAccount.setCustomer(mockCustomer);

        when(customerRepo.findById(customerId)).thenReturn(Optional.of(mockCustomer));
        when(accountRepo.save(any(BankAccount.class))).thenReturn(mockAccount);

        BankAccount result = bankAccountService.createBankAccount(customerId, "Checking", 1000);

        assertNotNull(result);
        assertEquals("Checking", result.getAccountType());
        assertEquals(1000, result.getAccountBalance());
        assertEquals("Michael", result.getCustomer().getFirstName());
        assertEquals("Jordan", result.getCustomer().getLastName());
    }

    @Test
    void testGetBankAccountById() {
        int customerId = 1;
        int accountId = 1;

        BankAccount mockAccount = new BankAccount();
        mockAccount.setAccountId(accountId);
        mockAccount.setAccountType("Checking");
        mockAccount.setAccountBalance(1000);

        when(accountRepo.findById(accountId)).thenReturn(Optional.of(mockAccount));

        Optional<BankAccount> result = bankAccountService.getBankAccountById(accountId);

        assertTrue(result.isPresent());
        assertEquals(customerId, result.get().getAccountId());
        assertEquals("Checking", result.get().getAccountType());
        assertEquals(1000, result.get().getAccountBalance());
    }

    @Test
    void testDeleteBankAccountByAccountId() {
        int accountId = 1;
        int customerId = 1;
        Customer mockCustomer = new Customer("Alicia", "Jobs");
        mockCustomer.setCustomerId(customerId);

        BankAccount mockAccount = new BankAccount();
        mockAccount.setAccountId(accountId);
        mockAccount.setAccountType("Checking");
        mockAccount.setAccountBalance(1000);

        when(accountRepo.findById(accountId)).thenReturn(Optional.of(mockAccount));

        boolean result = bankAccountService.deleteBankAccount(accountId);

        assertTrue(result);
        verify(accountRepo, times(1)).deleteById(accountId);
    }

    @Test
    void testDeleteBankAccountsByCustomerId() {
        int customerId = 1;
        Customer mockCustomer = new Customer("Alicia", "Jobs");
        mockCustomer.setCustomerId(customerId);

        BankAccount mockAccount1 = new BankAccount();
        mockAccount1.setAccountType("Checking");
        mockAccount1.setAccountBalance(1000);

        BankAccount mockAccount2 = new BankAccount();
        mockAccount2.setAccountType("Saving");
        mockAccount2.setAccountBalance(25000);

        List<BankAccount> accounts = Arrays.asList(mockAccount1, mockAccount2);
        when(accountRepo.findByCustomerId(customerId)).thenReturn(accounts);

        boolean result = bankAccountService.deleteBankAccounts(customerId);

        assertTrue(result);
        verify(accountRepo, times(1)).deleteAll(accounts);
    }
}
