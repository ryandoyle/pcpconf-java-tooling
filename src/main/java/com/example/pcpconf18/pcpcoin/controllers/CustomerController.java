package com.example.pcpconf18.pcpcoin.controllers;

import com.example.pcpconf18.pcpcoin.domain.NewCustomer;
import com.example.pcpconf18.pcpcoin.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

import static com.example.pcpconf18.pcpcoin.controllers.ResourceCreatedResponse.of;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody NewCustomer newCustomer) {
        Integer customerId = customerService.createNewCustomer(newCustomer.getName());

        return ResponseEntity.created(URI.create("/customer/" + customerId)).body(of("/customer/" + customerId));
    }

}
