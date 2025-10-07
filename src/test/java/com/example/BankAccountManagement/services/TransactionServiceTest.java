package com.example.BankAccountManagement.services;

import com.example.BankAccountManagement.entities.BankAccount;
import com.example.BankAccountManagement.entities.Transaction;
import com.example.BankAccountManagement.repositories.AccountRepo;
import com.example.BankAccountManagement.repositories.TransactionRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private TransactionRepo transactionRepo;

    @Test
    void testCreateTransaction() {
        int accountId = 1;
        BankAccount mockAccount = new BankAccount();
        mockAccount.setAccountId(accountId);

        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransactionType("Deposit");
        mockTransaction.setAmount(500);
        mockTransaction.setBankAccount(mockAccount);

        when(accountRepo.findById(accountId)).thenReturn(Optional.of(mockAccount));
        when(transactionRepo.save(any(Transaction.class))).thenReturn(mockTransaction);

        Transaction result = transactionService.createTransaction(accountId, "Deposit", 500);

        assertNotNull(result);
        assertEquals("Deposit", result.getTransactionType());
        assertEquals(500, result.getAmount());
        assertEquals(accountId, result.getBankAccount().getAccountId());
    }

    @Test
    void testGetTransactionById() {
        int transactionId = 1;

        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransactionId(transactionId);
        mockTransaction.setTransactionType("Withdrawal");
        mockTransaction.setAmount(200);

        when(transactionRepo.findById(transactionId)).thenReturn(Optional.of(mockTransaction));
        Optional<Transaction> result = transactionService.getTransactionById(transactionId);

        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(transactionId, result.get().getTransactionId());
        assertEquals("Withdrawal", result.get().getTransactionType());
        assertEquals(200, result.get().getAmount());
    }

    @Test
    void testGetTransactionsByBankAccountId() {
        int accountId = 1;

        BankAccount mockAccount = new BankAccount();
        mockAccount.setAccountId(accountId);

        Transaction mockTransaction1 = new Transaction();
        mockTransaction1.setTransactionId(1);
        mockTransaction1.setTransactionType("Deposit");
        mockTransaction1.setAmount(300);
        mockTransaction1.setBankAccount(mockAccount);

        Transaction mockTransaction2 = new Transaction();
        mockTransaction2.setTransactionId(2);
        mockTransaction2.setTransactionType("Withdrawal");
        mockTransaction2.setAmount(100);
        mockTransaction2.setBankAccount(mockAccount);

        when(transactionRepo.findByBankAccountAccountID(accountId))
                .thenReturn(java.util.Arrays.asList(mockTransaction1, mockTransaction2));

        java.util.List<Transaction> result = transactionService.getTransactionsByAccountId(accountId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Deposit", result.get(0).getTransactionType());
        assertEquals(300, result.get(0).getAmount());
        assertEquals("Withdrawal", result.get(1).getTransactionType());
        assertEquals(100, result.get(1).getAmount());
    }

    @Test
    void testDeleteTransactionById() {
        int transactionId = 1;

        Transaction mockTransaction = new Transaction();
        mockTransaction.setTransactionId(transactionId);
        mockTransaction.setTransactionType("Deposit");
        mockTransaction.setAmount(400);

        when(transactionRepo.findById(transactionId)).thenReturn(Optional.of(mockTransaction));

        boolean result = transactionService.deleteTransaction(transactionId);

        assertTrue(result);
    }

    @Test
    void testDeleteTransactionsByAccountId() {
        int accountId = 1;

        BankAccount mockAccount = new BankAccount();
        mockAccount.setAccountId(accountId);

        Transaction mockTransaction1 = new Transaction();
        mockTransaction1.setTransactionId(1);
        mockTransaction1.setTransactionType("Deposit");
        mockTransaction1.setAmount(300);
        mockTransaction1.setBankAccount(mockAccount);

        Transaction mockTransaction2 = new Transaction();
        mockTransaction2.setTransactionId(2);
        mockTransaction2.setTransactionType("Withdrawal");
        mockTransaction2.setAmount(100);
        mockTransaction2.setBankAccount(mockAccount);

        when(transactionRepo.findByBankAccountAccountID(accountId))
                .thenReturn(java.util.Arrays.asList(mockTransaction1, mockTransaction2));

        boolean result = transactionService.deleteTransactions(accountId);

        assertTrue(result);
    }
}
