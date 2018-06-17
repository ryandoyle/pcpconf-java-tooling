package com.example.pcpconf18.pcpcoin.service;

import com.example.pcpconf18.pcpcoin.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public UUID newTransaction(Integer from, Integer to, Long amount) {
        if (customerHasEnoughFunds(from, amount)) {
            return transactionRepository.createTransaction(from, to, amount);
        }
        throw new NotEnoughFundsException();
    }

    private boolean customerHasEnoughFunds(Integer from, Long amount) {
        return transactionRepository.getBalance(from) - amount > 0;
    }

}
