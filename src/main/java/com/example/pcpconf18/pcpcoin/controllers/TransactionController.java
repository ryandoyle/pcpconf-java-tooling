package com.example.pcpconf18.pcpcoin.controllers;

import com.example.pcpconf18.pcpcoin.domain.NewTransaction;
import com.example.pcpconf18.pcpcoin.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

import static com.example.pcpconf18.pcpcoin.controllers.ResourceCreatedResponse.of;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> submitTransaction(@Valid @RequestBody NewTransaction newTransaction) {
        UUID transactionId = transactionService.newTransaction(newTransaction.getFrom(),
                newTransaction.getTo(), newTransaction.getAmount());

        return ResponseEntity
                .created(URI.create("/transaction/" + transactionId))
                .body(of("/transaction/" + transactionId));
    }

}
