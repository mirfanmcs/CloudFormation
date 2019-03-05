package com.transaction;


import com.transaction.dto.TransactionResponseDto;
import com.transaction.entity.Transaction;
import com.transaction.repository.TransactionRepository;
import com.transaction.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTests {

    @Mock
    TransactionRepository transactionRepositoryMock;

    @InjectMocks
    TransactionService transactionService;

    @Test
    public void testGetTransactions() {
        when(transactionRepositoryMock.findTransactionsByAccountId(anyString())).thenReturn(getTransactions());
        assertEquals("0421111111", transactionService.getTransactions("123").get(0).getTelNo());
    }

    List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<Transaction>();

        Transaction transaction = new Transaction();
        transaction.setAccountId("123");
        transaction.setTime("11:30pm");
        transaction.setOrigin("Sydney");
        transaction.setDestination("Blacktown");
        transaction.setTelNo("0421111111");
        transaction.setDuration("1 min");
        transaction.setUnit("3");
        transaction.setDate("31 Oct 2018");

        transactions.add(transaction);

        return transactions;

    }
}

