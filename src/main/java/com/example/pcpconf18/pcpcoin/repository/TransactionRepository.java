package com.example.pcpconf18.pcpcoin.repository;

import com.example.pcpconf18.pcpcoin.service.InvalidRecipientException;
import org.springframework.stereotype.Repository;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

@Repository
public class TransactionRepository {

    private final Random random = new SecureRandom();


    public Integer getBalance(Integer unused) {
        return random.nextInt(50000);
    }

    public UUID createTransaction(Integer from, Integer to, Long amount) {
        /* Create some other random failures */
        if (random.nextInt() > 0x75_00_00_00) {
            throw new InvalidRecipientException();
        }
        return UUID.randomUUID();
    }


}
