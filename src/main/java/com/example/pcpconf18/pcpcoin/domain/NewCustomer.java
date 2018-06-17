package com.example.pcpconf18.pcpcoin.domain;

import javax.validation.constraints.NotNull;

public class NewCustomer {

    @NotNull
    private String name;

    public String getName() {
        return name;
    }
}
