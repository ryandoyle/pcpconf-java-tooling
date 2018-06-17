package com.example.pcpconf18.pcpcoin.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRecipientException extends RuntimeException {

    public InvalidRecipientException() {
        super("user ID is invalid");
    }
}
