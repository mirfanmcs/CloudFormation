package com.transaction.service;

import com.transaction.dto.TransactionResponseDto;
import com.transaction.entity.Transaction;
import com.transaction.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    private ModelMapper modelMapper = new ModelMapper();


    public List<TransactionResponseDto> getTransactions(String accountId) {

        List<Transaction> transactions = transactionRepository.findTransactionsByAccountId(accountId);
        Type listType = new TypeToken<List<TransactionResponseDto>>() {}.getType();
        List<TransactionResponseDto> transactionResponseDto =  modelMapper.map(transactions, listType);
        return transactionResponseDto;
    }
}
