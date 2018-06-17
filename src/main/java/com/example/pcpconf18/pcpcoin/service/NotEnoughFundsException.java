package com.example.pcpconf18.pcpcoin.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NotEnoughFundsException extends RuntimeException {

    public NotEnoughFundsException() {
        super("There are not enough funds in your account");
    }
}
