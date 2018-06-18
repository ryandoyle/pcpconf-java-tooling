package com.example.pcpconf18.pcpcoin.service;

import com.example.pcpconf18.pcpcoin.repository.CustomerRepository;
import io.pcp.parfait.spring.Profiled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Profiled
    public Integer createNewCustomer(String name) {
        return customerRepository.newCustomer(name);
    }

}
