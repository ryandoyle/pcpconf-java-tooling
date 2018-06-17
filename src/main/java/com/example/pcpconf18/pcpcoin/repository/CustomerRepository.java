package com.example.pcpconf18.pcpcoin.repository;

import org.springframework.stereotype.Repository;

import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class CustomerRepository {

    private static final int FIRST_CUSTOMER_ID = 23789;
    private final AtomicInteger customerId = new AtomicInteger(FIRST_CUSTOMER_ID);

    public Integer newCustomer(String unused) {
        return customerId.incrementAndGet();
    }

}
