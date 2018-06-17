package com.example.pcpconf18.pcpcoin.domain;

import javax.validation.constraints.NotNull;

public class NewTransaction {

    @NotNull
    private Integer from;
    @NotNull
    private Integer to;
    @NotNull
    private Long amount;

    public Integer getFrom() {
        return from;
    }

    public Integer getTo() {
        return to;
    }

    public Long getAmount() {
        return amount;
    }
}
